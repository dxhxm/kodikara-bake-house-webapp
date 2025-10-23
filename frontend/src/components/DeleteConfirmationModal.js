import React from 'react';
import Modal from './Modal';

const DeleteConfirmationModal = ({ show, onHide, onConfirm, shop }) => {
    return (
        <Modal show={show} onHide={onHide} title="Delete Shop" footer={<><button className="btn btn-secondary" onClick={onHide}>Cancel</button><button className="btn btn-danger" onClick={onConfirm}>Delete</button></>}>
            <p>Are you sure you want to delete the shop "{shop ? shop.name : ''}"?</p>
        </Modal>
    );
};

export default DeleteConfirmationModal;