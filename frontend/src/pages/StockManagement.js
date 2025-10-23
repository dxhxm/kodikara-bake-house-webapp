import React, { useState, useEffect } from 'react';
import http from '../api/http';
// ShopService is no longer imported

const StockManagement = () => {
  // Hardcoded shops as requested
  const hardcodedShops = [
    { shopId: 1, name: 'Shop' },
    { shopId: 2, name: 'Truck 1' },
    { shopId: 3, name: 'Truck 2' },
  ];

  const [shops, setShops] = useState(hardcodedShops);
  const [selectedShop, setSelectedShop] = useState(hardcodedShops[0].shopId); // Select first hardcoded shop by default
  const [date, setDate] = useState(new Date().toISOString().split('T')[0]); // YYYY-MM-DD
  const [products, setProducts] = useState([]);
  const [stockData, setStockData] = useState({}); // { productId: { morning: N, closing: N } }
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [report, setReport] = useState([]); // For displaying calculated report

  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);
        // Shops are now hardcoded, no need to fetch
        // setShops(fetchedShops);
        // if (fetchedShops.length > 0) {
        //   setSelectedShop(fetchedShops[0].shopId); 
        // }

        // Fetch products
        const productsResponse = await http.get('/api/products');
        setProducts(productsResponse.data);

        setError(null);
      } catch (err) {
        setError(err.message || 'Failed to fetch initial data.');
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, []);

  // Fetch stock data for selected shop and date
  useEffect(() => {
    if (selectedShop && date) {
      const fetchStock = async () => {
        try {
          setLoading(true);
          const response = await http.get(`/api/stock/${selectedShop}/${date}`);
          const fetchedStockReport = response.data;
          setReport(fetchedStockReport);

          // Populate stockData for editing
          const initialStockData = {};
          fetchedStockReport.forEach(item => {
            initialStockData[item.productId] = {
              morning: item.morningQuantity,
              closing: item.closingQuantity,
            };
          });
          setStockData(initialStockData);
        } catch (err) {
          setError(err.message || 'Failed to fetch stock data.');
          setReport([]);
          setStockData({});
        } finally {
          setLoading(false);
        }
      };
      fetchStock();
    }
  }, [selectedShop, date]);

  const [searchTerm, setSearchTerm] = useState('');

  const handleMorningChange = (productId, value) => {
    const intValue = parseInt(value) || 0;
    if (intValue >= 0) {
      setStockData(prev => ({
        ...prev,
        [productId]: { ...prev[productId], morning: intValue },
      }));
    }
  };

  const handleClosingChange = (productId, value) => {
    const intValue = parseInt(value) || 0;
    const morningStock = stockData[productId]?.morning || 0;
    if (intValue >= 0 && intValue <= morningStock) {
      setStockData(prev => ({
        ...prev,
        [productId]: { ...prev[productId], closing: intValue },
      }));
    }
  };

  const handleSaveStock = async () => {
    try {
      setLoading(true);
      const stockEntries = products.map(product => ({
        productId: product.proId,
        shopId: selectedShop,
        date: date,
        morningQuantity: stockData[product.proId]?.morning || 0,
        closingQuantity: stockData[product.proId]?.closing || 0,
      }));

      // Send each entry individually or as a batch if backend supports
      for (const entry of stockEntries) {
        await http.post('/api/stock', entry);
      }
      setError(null);
      // Re-fetch report after saving
      const response = await http.get(`/api/stock/${selectedShop}/${date}`);
      setReport(response.data);

    } catch (err) {
      setError(err.message || 'Failed to save stock data.');
    } finally {
      setLoading(false);
    }
  };

  const filteredProducts = products.filter(product =>
    (product.name && product.name.toLowerCase().includes(searchTerm.toLowerCase())) ||
    (product.category && product.category.toLowerCase().includes(searchTerm.toLowerCase()))
  );

  if (loading) return <p className="container mt-4">Loading stock management data...</p>;
  if (error) return <div className="alert alert-danger container mt-4">Error: {error}</div>;

  return (
    <div className="container mt-4">
      <h2>Stock Management</h2>

      <div className="row mb-3">
        <div className="col-md-4">
          <label htmlFor="shopSelect" className="form-label">Select Location</label>
          <select 
            id="shopSelect" 
            className="form-select"
            value={selectedShop}
            onChange={(e) => setSelectedShop(parseInt(e.target.value))}
          >
            {shops.map(shop => (
              <option key={shop.shopId} value={shop.shopId}>{shop.name}</option>
            ))}
          </select>
        </div>
        <div className="col-md-4">
          <label htmlFor="dateSelect" className="form-label">Select Date</label>
          <input 
            type="date" 
            id="dateSelect" 
            className="form-control"
            value={date}
            onChange={(e) => setDate(e.target.value)}
          />
        </div>
        <div className="col-md-4 d-flex align-items-end">
          <button className="btn btn-success w-100" onClick={handleSaveStock}>Save Daily Stock</button>
        </div>
      </div>

      <div className="mb-3">
        <input
          type="text"
          className="form-control"
          placeholder="Search by name or category..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
      </div>

      <h3 className="mt-4">Daily Stock Report for {shops.find(s => s.shopId === selectedShop)?.name} on {date}</h3>
      
      {filteredProducts.length === 0 ? (
        <p>No products available to manage stock.</p>
      ) : (
        <table className="table table-striped table-hover">
          <thead>
            <tr>
              <th>Product ID</th>
              <th>Product Name</th>
              <th>Morning Stock</th>
              <th>Closing Stock</th>
              <th>Stock Sold</th>
              <th>Income</th>
            </tr>
          </thead>
          <tbody>
            {filteredProducts.map(product => {
              const itemReport = report.find(r => r.productId === product.proId) || {};
              const morning = stockData[product.proId]?.morning || itemReport.morningQuantity || 0;
              const closing = stockData[product.proId]?.closing || itemReport.closingQuantity || 0;
              const stockSold = morning - closing;
              const income = itemReport.income || 0;
              const remaining = closing;

              return (
                <tr key={product.proId}>
                  <td>{product.proId}</td>
                  <td>{product.name}</td>
                  <td>
                    <input 
                      type="number" 
                      className="form-control form-control-sm"
                      value={morning}
                      onChange={(e) => handleMorningChange(product.proId, e.target.value)}
                    />
                  </td>
                  <td>
                    <input 
                      type="number" 
                      className="form-control form-control-sm"
                      value={closing}
                      onChange={(e) => handleClosingChange(product.proId, e.target.value)}
                    />
                  </td>
                  <td>{stockSold}</td>
                  <td>LKR {income.toFixed(2)}</td>
                </tr>
              );
            })}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default StockManagement;