const mongoose = require('mongoose');

const criminalSchema = mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    
        name: {
            required: true,
            type: String
        },
        descriptors: [],
        crimes: [],
        image: String
    
});

module.exports = mongoose.model('Criminal', criminalSchema);