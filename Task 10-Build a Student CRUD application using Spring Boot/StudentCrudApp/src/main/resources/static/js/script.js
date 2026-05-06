// Form validation and enhancements
document.addEventListener('DOMContentLoaded', function() {
    // Auto-hide alerts after 5 seconds
    setTimeout(function() {
        const alerts = document.querySelectorAll('.alert');
        alerts.forEach(function(alert) {
            alert.style.transition = 'opacity 0.5s ease';
            alert.style.opacity = '0';
            setTimeout(function() {
                alert.remove();
            }, 500);
        });
    }, 5000);
    
    // Form validation for add/edit forms
    const forms = document.querySelectorAll('.student-form');
    forms.forEach(function(form) {
        form.addEventListener('submit', function(event) {
            const emailInput = form.querySelector('input[type="email"]');
            const phoneInput = form.querySelector('input[name="phoneNumber"]');
            
            // Email validation
            if (emailInput && emailInput.value) {
                const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                if (!emailRegex.test(emailInput.value)) {
                    event.preventDefault();
                    showCustomAlert('Please enter a valid email address', 'error');
                }
            }
            
            // Phone number validation (optional)
            if (phoneInput && phoneInput.value) {
                const phoneRegex = /^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$/;
                if (!phoneRegex.test(phoneInput.value)) {
                    event.preventDefault();
                    showCustomAlert('Please enter a valid phone number', 'error');
                }
            }
        });
    });
    
    // Add confirmation for delete buttons
    const deleteButtons = document.querySelectorAll('.btn-delete');
    deleteButtons.forEach(function(button) {
        button.addEventListener('click', function(event) {
            if (!confirm('Are you sure you want to delete this student? This action cannot be undone.')) {
                event.preventDefault();
            }
        });
    });
    
    // Function to show custom alerts
    function showCustomAlert(message, type) {
        const alertDiv = document.createElement('div');
        alertDiv.className = `alert alert-${type}`;
        alertDiv.textContent = message;
        
        const container = document.querySelector('.container');
        container.insertBefore(alertDiv, container.firstChild);
        
        setTimeout(function() {
            alertDiv.style.transition = 'opacity 0.5s ease';
            alertDiv.style.opacity = '0';
            setTimeout(function() {
                alertDiv.remove();
            }, 500);
        }, 3000);
    }
    
    // Add smooth scrolling
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            document.querySelector(this.getAttribute('href')).scrollIntoView({
                behavior: 'smooth'
            });
        });
    });
    
    // Add animation to table rows
    const tableRows = document.querySelectorAll('.student-table tbody tr');
    tableRows.forEach((row, index) => {
        row.style.animation = `fadeIn 0.3s ease forwards ${index * 0.05}s`;
        row.style.opacity = '0';
    });
    
    // Add CSS for animations
    const style = document.createElement('style');
    style.textContent = `
        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(10px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
    `;
    document.head.appendChild(style);
    
    // Format phone number input
    const phoneInputs = document.querySelectorAll('input[name="phoneNumber"]');
    phoneInputs.forEach(input => {
        input.addEventListener('input', function(e) {
            let value = e.target.value.replace(/\D/g, '');
            if (value.length > 0) {
                if (value.length <= 3) {
                    value = value;
                } else if (value.length <= 6) {
                    value = value.slice(0, 3) + '-' + value.slice(3);
                } else {
                    value = value.slice(0, 3) + '-' + value.slice(3, 6) + '-' + value.slice(6, 10);
                }
                e.target.value = value;
            }
        });
    });
    
    // Add tooltips to action buttons
    const actionButtons = document.querySelectorAll('.actions a');
    actionButtons.forEach(button => {
        button.setAttribute('data-tooltip', button.textContent.trim());
    });
    
    // Add tooltip CSS
    const tooltipStyle = document.createElement('style');
    tooltipStyle.textContent = `
        [data-tooltip] {
            position: relative;
            cursor: pointer;
        }
        
        [data-tooltip]:before {
            content: attr(data-tooltip);
            position: absolute;
            bottom: 100%;
            left: 50%;
            transform: translateX(-50%);
            padding: 5px 10px;
            background: var(--bg-tertiary);
            color: var(--text-primary);
            border-radius: 5px;
            font-size: 12px;
            white-space: nowrap;
            opacity: 0;
            visibility: hidden;
            transition: all 0.3s ease;
            border: 1px solid var(--border-color);
            z-index: 1000;
        }
        
        [data-tooltip]:hover:before {
            opacity: 1;
            visibility: visible;
            bottom: 120%;
        }
    `;
    document.head.appendChild(tooltipStyle);
});