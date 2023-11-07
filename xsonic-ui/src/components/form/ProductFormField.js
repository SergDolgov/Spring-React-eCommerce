import React from 'react';

const ProductFormField = ({ type, name, label, value, onChange, required }) => {
    return (
        <div className="input_box">
            <input
                type={type}
                name={name}
                className="input_field"
                value={value || ''}
                onChange={onChange}
                required={required}
            />
            <label className="input_label">{label}</label>
        </div>
    );
};

export default ProductFormField;