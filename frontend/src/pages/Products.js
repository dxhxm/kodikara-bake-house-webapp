import React, { useState, useEffect } from 'react';
import http from '../api/http';
import ProductTable from '../components/ProductTable';
import ProductFormModal from '../components/ProductFormModal';
import DeleteConfirmationModal from '../components/DeleteConfirmationModal';

const Products = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const categories = ['Buns', 'Pastries', 'Short Eats', 'Breads', 'Sandwiches', 'Biscuits', 'Sweets', 'Beverages', 'Cakes'];

  // State for modals
  const [showFormModal, setShowFormModal] = useState(false);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [selectedProduct, setSelectedProduct] = useState(null);
  const [newProductId, setNewProductId] = useState(null); // New state for generated ID

  const fetchProducts = async () => {
    try {
      setLoading(true);
      const response = await http.get('/api/products');
      setProducts(response.data);
      setError(null);
    } catch (err) {
      setError(err.message || 'Failed to fetch products. Is the backend running correctly?');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  const handleSave = async (productData) => {
    try {
      if (selectedProduct && selectedProduct.proId) {
        // Update existing product
        await http.put(`/api/products/${selectedProduct.proId}`, productData);
      } else {
        // Create new product (use newProductId if available, otherwise from form)
        await http.post('/api/products', { ...productData, proId: newProductId || productData.proId });
      }
      setShowFormModal(false);
      setSelectedProduct(null);
      setNewProductId(null); // Clear generated ID
      fetchProducts(); // Refresh the list
    } catch (err) {
      setError(err.message || 'Failed to save product.');
    }
  };

  const handleDelete = async () => {
    if (!selectedProduct) return;
    try {
      await http.delete(`/api/products/${selectedProduct.proId}`);
      setShowDeleteModal(false);
      setSelectedProduct(null);
      fetchProducts(); // Refresh the list
    } catch (err) {
      setError(err.message || 'Failed to delete product.');
    }
  };

  // Modal handlers
  const handleEdit = (product) => {
    setSelectedProduct(product);
    setNewProductId(null); // Ensure no new ID is active when editing
    setShowFormModal(true);
  };

  const handleDeleteClick = (product) => {
    setSelectedProduct(product);
    setShowDeleteModal(true);
  };

  const handleAddProductClick = async () => {
    try {
      const response = await http.get('/api/products/nextId');
      const nextProId = response.data;
      setNewProductId(nextProId); // Set the generated ID
      setSelectedProduct(null); // Ensure it's add mode
      setShowFormModal(true);
    } catch (err) {
      setError(err.message || 'Failed to generate new product ID.');
    }
  };

  const filteredProducts = products.filter(product =>
    (product.name && product.name.toLowerCase().includes(searchTerm.toLowerCase())) ||
    (product.category && product.category.toLowerCase().includes(searchTerm.toLowerCase()))
  );

  return (
    <div className="container mt-4">
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h2>Product Management</h2>
        <div>
          <button className="btn btn-primary" onClick={handleAddProductClick}>Add Product</button>
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

      {error && <div className="alert alert-danger">Error: {error}</div>}

      {loading ? (
        <p>Loading products...</p>
      ) : (
        <>
          {filteredProducts.length > 0 ? (
            <ProductTable products={filteredProducts} onEdit={handleEdit} onDelete={handleDeleteClick} />
          ) : (
            <div className="text-center mt-4">
              <p>No products match your search.</p>
            </div>
          )}
        </>
      )}

      <ProductFormModal 
        show={showFormModal} 
        onHide={() => {
          setShowFormModal(false);
          setSelectedProduct(null); // Clear selected product on hide
          setNewProductId(null); // Clear generated ID on hide
        }}
        onSave={handleSave} 
        product={selectedProduct} 
        newProductId={newProductId} // Pass newProductId
        categories={categories} // Pass hardcoded categories to the form
      />

      <DeleteConfirmationModal 
        show={showDeleteModal} 
        onHide={() => setShowDeleteModal(false)} 
        onConfirm={handleDelete} 
        product={selectedProduct} 
      />
    </div>
  );
};

export default Products;