import React, { useState, useEffect } from 'react';
import http from '../api/http';
import ProductTable from '../components/ProductTable';
import ProductFormModal from '../components/ProductFormModal';
import DeleteConfirmationModal from '../components/DeleteConfirmationModal';
// CategoryManagementModal is no longer imported

const Products = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  // Hardcoded categories as requested
  const categories = ['Buns', 'Pastries', 'Short Eats', 'Breads', 'Sandwiches', 'Biscuits', 'Sweets', 'Beverages', 'Cakes'];

  // State for modals
  const [showFormModal, setShowFormModal] = useState(false);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  // showCategoryManagementModal is no longer needed
  const [selectedProduct, setSelectedProduct] = useState(null);

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
      if (selectedProduct) {
        await http.put(`/api/products/${selectedProduct.proId}`, productData);
      } else {
        await http.post('/api/products', productData);
      }
      setShowFormModal(false);
      setSelectedProduct(null);
      fetchProducts();
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
      fetchProducts();
    } catch (err) {
      setError(err.message || 'Failed to delete product.');
    }
  };

  // Modal handlers
  const handleEdit = (product) => {
    setSelectedProduct(product);
    setShowFormModal(true);
  };

  const handleDeleteClick = (product) => {
    setSelectedProduct(product);
    setShowDeleteModal(true);
  };

  const handleAddProductClick = () => {
    setSelectedProduct(null);
    setShowFormModal(true);
  };

  // handleCategoryManagementClick is no longer needed

  return (
    <div className="container mt-4">
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h2>Product Management</h2>
        <div>
          {/* Edit Categories button removed */}
          <button className="btn btn-primary" onClick={handleAddProductClick}>Add Product</button>
        </div>
      </div>

      {error && <div className="alert alert-danger">Error: {error}</div>}

      {loading ? (
        <p>Loading products...</p>
      ) : (
        <ProductTable products={products} onEdit={handleEdit} onDelete={handleDeleteClick} />
      )}

      <ProductFormModal 
        show={showFormModal} 
        onHide={() => setShowFormModal(false)} 
        onSave={handleSave} 
        product={selectedProduct} 
        categories={categories} // Pass hardcoded categories to the form
      />

      <DeleteConfirmationModal 
        show={showDeleteModal} 
        onHide={() => setShowDeleteModal(false)} 
        onConfirm={handleDelete} 
        product={selectedProduct} 
      />

      {/* CategoryManagementModal is no longer rendered */}
    </div>
  );
};

export default Products;