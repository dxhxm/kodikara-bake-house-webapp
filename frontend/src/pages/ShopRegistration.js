import React, { useState, useEffect } from 'react';
import http from '../api/http';
import ShopFormModal from '../components/ShopFormModal';
import DeleteConfirmationModal from '../components/DeleteConfirmationModal';
import ShopTable from '../components/ShopTable';

const ShopRegistration = () => {
    const [shops, setShops] = useState([]);
    const [error, setError] = useState(null);
    const [editingShop, setEditingShop] = useState(null);
    const [showFormModal, setShowFormModal] = useState(false);
    const [showDeleteModal, setShowDeleteModal] = useState(false);

    useEffect(() => {
        fetchShops();
    }, []);

    const fetchShops = async () => {
        try {
            const response = await http.get('/api/shops');
            setShops(response.data);
        } catch (err) {
            setError(err.message || 'Failed to fetch shops.');
        }
    };

    const handleFormSubmit = async (shopData) => {
        try {
            if (editingShop) {
                await http.put(`/api/shops/${editingShop.shopId}`, shopData);
            } else {
                await http.post('/api/shops', shopData);
            }
            setShowFormModal(false);
            fetchShops();
        } catch (err) {
            setError(err.response?.data?.message || err.message || 'Failed to save shop.');
        }
    };

    const handleEdit = (shop) => {
        setEditingShop(shop);
        setShowFormModal(true);
    };

    const handleDelete = async () => {
        try {
            await http.delete(`/api/shops/${editingShop.shopId}`);
            setShowDeleteModal(false);
            fetchShops();
        } catch (err) {
            setError(err.message || 'Failed to delete shop.');
        }
    };

    const openAddShopModal = () => {
        setEditingShop(null);
        setShowFormModal(true);
    }

    return (
        <div className="container mt-4">
            <h2>Shop Registration</h2>
            {error && <div className="alert alert-danger">{error}</div>}
            <button onClick={openAddShopModal} className="btn btn-primary mb-3">Add Shop</button>

            <h3>Registered Shops</h3>
            <ShopTable shops={shops} onEdit={handleEdit} onDelete={(shop) => { setEditingShop(shop); setShowDeleteModal(true); }} />

            <ShopFormModal show={showFormModal} onHide={() => setShowFormModal(false)} onSave={handleFormSubmit} shop={editingShop} />
            <DeleteConfirmationModal show={showDeleteModal} onHide={() => setShowDeleteModal(false)} onConfirm={handleDelete} shop={editingShop} />
        </div>
    );
};

export default ShopRegistration;
