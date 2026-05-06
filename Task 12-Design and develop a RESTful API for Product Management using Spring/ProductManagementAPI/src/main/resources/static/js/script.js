// API Base URL
const API_BASE_URL = '/api/products';

// Load products when page loads
document.addEventListener('DOMContentLoaded', function() {
    loadProducts();
});

// Load all products
function loadProducts() {
    showLoading();
    
    fetch(API_BASE_URL)
        .then(response => response.json())
        .then(products => {
            displayProducts(products);
            hideLoading();
        })
        .catch(error => {
            console.error('Error loading products:', error);
            showError('Failed to load products');
            hideLoading();
        });
}

// Display products in table
function displayProducts(products) {
    const tbody = document.getElementById('productTableBody');
    tbody.innerHTML = '';
    
    if (products.length === 0) {
        tbody.innerHTML = '<tr><td colspan="7" style="text-align: center;">No products found</td></tr>';
        return;
    }
    
    products.forEach(product => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${product.id}</td>
            <td>${escapeHtml(product.name)}</td>
            <td>${escapeHtml(product.description || '-')}</td>
            <td>$${product.price.toFixed(2)}</td>
            <td>${product.quantity}</td>
            <td>${product.category || '-'}</td>
            <td>
                <button class="action-btn edit-btn" onclick="editProduct(${product.id})">✏️ Edit</button>
                <button class="action-btn delete-btn" onclick="deleteProduct(${product.id})">🗑️ Delete</button>
            </td>
        `;
        tbody.appendChild(row);
    });
}

// Show add product form
function showAddForm() {
    document.getElementById('formTitle').textContent = '➕ Add New Product';
    document.getElementById('productFormElement').reset();
    document.getElementById('productId').value = '';
    document.getElementById('productForm').style.display = 'block';
    document.getElementById('products').scrollIntoView({ behavior: 'smooth' });
}

// Hide form
function hideForm() {
    document.getElementById('productForm').style.display = 'none';
}

// Edit product
function editProduct(id) {
    fetch(`${API_BASE_URL}/${id}`)
        .then(response => response.json())
        .then(product => {
            document.getElementById('formTitle').textContent = '✏️ Edit Product';
            document.getElementById('productId').value = product.id;
            document.getElementById('name').value = product.name;
            document.getElementById('description').value = product.description || '';
            document.getElementById('price').value = product.price;
            document.getElementById('quantity').value = product.quantity;
            document.getElementById('category').value = product.category || '';
            
            document.getElementById('productForm').style.display = 'block';
            document.getElementById('products').scrollIntoView({ behavior: 'smooth' });
        })
        .catch(error => {
            console.error('Error loading product:', error);
            showError('Failed to load product details');
        });
}

// Save product (Create or Update)
function saveProduct(event) {
    event.preventDefault();
    
    const productId = document.getElementById('productId').value;
    const productData = {
        name: document.getElementById('name').value,
        description: document.getElementById('description').value,
        price: parseFloat(document.getElementById('price').value),
        quantity: parseInt(document.getElementById('quantity').value),
        category: document.getElementById('category').value
    };
    
    const url = productId ? `${API_BASE_URL}/${productId}` : API_BASE_URL;
    const method = productId ? 'PUT' : 'POST';
    
    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(productData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to save product');
        }
        return response.json();
    })
    .then(() => {
        hideForm();
        loadProducts();
        showSuccess(productId ? 'Product updated successfully!' : 'Product created successfully!');
    })
    .catch(error => {
        console.error('Error saving product:', error);
        showError('Failed to save product');
    });
}

// Delete product
function deleteProduct(id) {
    if (confirm('Are you sure you want to delete this product?')) {
        fetch(`${API_BASE_URL}/${id}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to delete product');
            }
            loadProducts();
            showSuccess('Product deleted successfully!');
        })
        .catch(error => {
            console.error('Error deleting product:', error);
            showError('Failed to delete product');
        });
    }
}

// Test GET API
function testGet() {
    const url = document.getElementById('getUrl').value;
    
    fetch(API_BASE_URL)
        .then(response => response.json())
        .then(data => {
            document.getElementById('getResponse').textContent = 
                JSON.stringify(data, null, 2);
        })
        .catch(error => {
            document.getElementById('getResponse').textContent = 
                'Error: ' + error.message;
        });
}

// Helper Functions
function escapeHtml(text) {
    if (!text) return '';
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

function showLoading() {
    const tbody = document.getElementById('productTableBody');
    tbody.innerHTML = `
        <tr>
            <td colspan="7">
                <div class="loading">
                    <div class="loading-spinner"></div>
                    <p>Loading products...</p>
                </div>
            </td>
        </tr>
    `;
}

function hideLoading() {
    // Loading is removed when products are displayed
}

function showSuccess(message) {
    alert('✅ ' + message); // You can enhance this with a toast notification
}

function showError(message) {
    alert('❌ ' + message); // You can enhance this with a toast notification
}

// Navigation highlighting
document.querySelectorAll('.nav-link').forEach(link => {
    link.addEventListener('click', function(e) {
        document.querySelectorAll('.nav-link').forEach(l => l.classList.remove('active'));
        this.classList.add('active');
    });
});