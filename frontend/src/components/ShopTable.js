import React from 'react';

const ShopTable = ({ shops, onEdit, onDelete }) => (
  <table className="table table-striped table-hover">
    <thead>
      <tr>
        <th>Name</th>
        <th>Location</th>
        <th>Contact</th>
        <th>Owner</th>
        <th>Actions</th>
      </tr>
    </thead>
    <tbody>
      {shops.length > 0 ? (
        shops.map(shop => (
          <tr key={shop.shopId}>
            <td>{shop.name}</td>
            <td>{shop.location}</td>
            <td>{shop.contactDetails}</td>
            <td>{shop.ownerName}</td>
            <td>
              <button className="btn btn-sm btn-primary me-2" onClick={() => onEdit(shop)}>Edit</button>
              <button className="btn btn-sm btn-danger" onClick={() => onDelete(shop)}>Delete</button>
            </td>
          </tr>
        ))
      ) : (
        <tr>
          <td colSpan="5" className="text-center">No shops found.</td>
        </tr>
      )}
    </tbody>
  </table>
);

export default ShopTable;
