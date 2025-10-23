import React, { useState, useEffect } from 'react';

const ProductFormModal = ({ show, onHide, onSave, product, newProductId, categories }) => {
  const [formData, setFormData] = useState({});

  useEffect(() => {
    // If product is provided, it's edit mode. Otherwise, it's add mode.
    // In add mode, use newProductId if available.
    setFormData(product || { 
      proId: newProductId || '', 
      name: '', 
      category: categories[0] || '', 
      unitPrice: 0 
    });
  }, [product, newProductId, categories]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSave(formData);
  };

  if (!show) return null;

  const isAddMode = !product || !product.proId; // True if no existing product or no proId

  return (
    <div className="modal show d-block" tabIndex="-1" style={{ backgroundColor: 'rgba(0,0,0,0.5)' }}>
      <div className="modal-dialog">
        <div className="modal-content">
          <form onSubmit={handleSubmit}>
            <div className="modal-header">
              <h5 className="modal-title">{isAddMode ? 'Add Product' : 'Edit Product'}</h5>
              <button type="button" className="btn-close" onClick={onHide}></button>
            </div>
            <div className="modal-body">
              <div className="mb-3">
                <label htmlFor="proId" className="form-label">Product ID</label>
                <input 
                  type="text" 
                  className="form-control" 
                  id="proId" 
                  name="proId" 
                  value={formData.proId || ''} 
                  onChange={handleChange} 
                  disabled={isAddMode || !!(product && product.proId)} // Disable if adding or if editing an existing product
                  required 
                />
              </div>
              <div className="mb-3">
                <label htmlFor="name" className="form-label">Name</label>
                <input type="text" className="form-control" id="name" name="name" value={formData.name || ''} onChange={handleChange} required />
              </div>
              <div className="mb-3">
                <label htmlFor="category" className="form-label">Category</label>
                <select className="form-select" id="category" name="category" value={formData.category || categories[0] || ''} onChange={handleChange} required>
                  {categories.map(cat => (
                    <option key={cat} value={cat}>{cat}</option>
                  ))}
                </select>
              </div>
              <div className="mb-3">
                <label htmlFor="unitPrice" className="form-label">Unit Price</label>
                <input type="number" step="0.01" className="form-control" id="unitPrice" name="unitPrice" value={formData.unitPrice || ''} onChange={handleChange} required />
              </div>
            </div>
            <div className="modal-footer">
              <button type="button" className="btn btn-secondary" onClick={onHide}>Close</button>
              <button type="submit" className="btn btn-primary">Save Changes</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default ProductFormModal;
