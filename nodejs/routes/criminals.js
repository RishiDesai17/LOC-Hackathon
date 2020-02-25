const express = require('express');
const router = express.Router();
const ProductController = require('../controllers/criminals');

router.get('/',ProductController.get_criminals)

router.post('/',ProductController.post_criminals)

router.patch('/:userId',ProductController.update)

module.exports = router;