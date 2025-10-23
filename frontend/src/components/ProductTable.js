import React from 'react';
import '../pages/dashboard.css';

const ProductTable = ({ products, onEdit, onDelete }) => (
  <table className="table table-striped table-hover">
    <thead>
      <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Category</th>
        <th>Unit Price</th>
        <th>Actions</th>
      </tr>
    </thead>
    <tbody>
      {products.length > 0 ? (
        products.map(product => (
          <tr key={product.proId}>
            <td>{product.proId}</td>
            <td>{product.name}</td>
            <td>{product.category}</td>
            <td>LKR {Number(product.unitPrice).toFixed(2)}</td>
            <td>
              <button className="btn btn-sm btn-primary me-2" onClick={() => onEdit(product)}>Edit</button>
              <button className="btn btn-sm btn-danger" onClick={() => onDelete(product)}>Delete</button>
            </td>
          </tr>
        ))
      ) : (
        <tr>
          <td colSpan="5" className="text-center">No products found.</td>
        </tr>
      )}
    </tbody>
  </table>
);

export default ProductTable;
