import React, { useState, useEffect } from 'react';
import Modal from './Modal';

const ShopFormModal = ({ show, onHide, onSave, shop }) => {
    const [formData, setFormData] = useState({ name: '', location: '', contactDetails: '', ownerName: '' });
    const [error, setError] = useState(null);

    useEffect(() => {
        if (shop) {
            setFormData(shop);
        } else {
            setFormData({ name: '', location: '', contactDetails: '', ownerName: '' });
        }
        setError(null);
    }, [shop, show]);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSave = () => {
        const { name, location, contactDetails, ownerName } = formData;
        if (!name || !location || !contactDetails || !ownerName) {
            setError('All fields are required.');
            return;
        }

        if (!/^\d{1,15}$/.test(contactDetails)) {
            setError('Contact number must contain only digits (max 15).');
            return;
        }

        onSave(formData);
    };

    return (
        <Modal show={show} onHide={onHide} title={shop ? 'Edit Shop' : 'Add Shop'} footer={<><button className="btn btn-secondary" onClick={onHide}>Close</button><button className="btn btn-primary" onClick={handleSave}>Save Changes</button></>}>
            {error && <div className="alert alert-danger">{error}</div>}
            <form>
                <div className="mb-3">
                    <label className="form-label">Shop Name</label>
                    <input type="text" name="name" value={formData.name} onChange={handleInputChange} className="form-control" required />
                </div>
                <div className="mb-3">
                    <label className="form-label">Location</label>
                    <input type="text" name="location" value={formData.location} onChange={handleInputChange} className="form-control" required />
                </div>
                <div className="mb-3">
                    <label className="form-label">Contact Details</label>
                    <input type="text" name="contactDetails" value={formData.contactDetails} onChange={handleInputChange} className="form-control" required />
                </div>
                <div className="mb-3">
                    <label className="form-label">Owner Name</label>
                    <input type="text" name="ownerName" value={formData.ownerName} onChange={handleInputChange} className="form-control" required />
                </div>
            </form>
        </Modal>
    );
};

export default ShopFormModal;
