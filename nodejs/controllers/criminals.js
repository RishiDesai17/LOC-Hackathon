const mongoose = require('mongoose');
const Criminal = require('../models/criminals');

exports.get_criminals = (req,res)=>{
    Criminal.find().exec().then((products)=>{
        res.json({
            docs: products
        })
    })
}

exports.post_criminals = (req,res)=>{
    // const descObj = req.body.descriptors;
    // var arr = Object.keys(descObj).map(function (key) {
    //     return { [key]: obj[key] };
    //   });

    const criminal = new Criminal({
        _id: new mongoose.Types.ObjectId(),
        name: req.body.name,
        descriptors: req.body.descriptors,
        crimes: req.body.crimes,
        image: req.body.image
    });
    criminal.save().then(result=>{
        console.log(result);
        res.status(201).json({
            message: "Product added",
            docs: result
        })
    }).catch(err=>{
        console.log(err);
        res.status(500).json({
            error: err
        })
    })
}
exports.update = (req,res,next)=>{
    const id = req.params.userId;
    Criminal.update({name: id}, {$set: req.body}).exec().then(result=>{
        res.status(200).json({
            message: 'Updated',
        })
    }).catch(err=>{
        console.log(err);
        res.status(500).json({
            error: err
        })
    })
}
