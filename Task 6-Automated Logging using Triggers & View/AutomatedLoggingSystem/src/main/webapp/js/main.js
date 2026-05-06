/**
 * Automated Logging System - Main JavaScript File
 * Handles UI interactions, animations, and real-time updates
 */

// Wait for DOM to be fully loaded
document.addEventListener('DOMContentLoaded', function() {
    console.log('✅ Automated Logging System initialized');
    
    // Initialize all components
    initializeTooltips();
    initializeLogViewer();
    initializeSearchFilter();
    initializeAutoRefresh();
    initializeCharts();
    initializeFormValidation();
    initializeNotifications();
    initializeThemeToggle();
});

/**
 * Initialize Bootstrap-style tooltips
 */
function initializeTooltips() {
    const tooltipElements = document.querySelectorAll('[data-tooltip]');
    tooltipElements.forEach(element => {
        element.addEventListener('mouseenter', showTooltip);
        element.addEventListener('mouseleave', hideTooltip);
    });
}

function showTooltip(event) {
    const element = event.target;
    const tooltipText = element.getAttribute('data-tooltip');
    
    const tooltip = document.createElement('div');
    tooltip.className = 'custom-tooltip';
    tooltip.textContent = tooltipText;
    tooltip.style.cssText = `
        position: absolute;
        background: var(--accent-primary);
        color: white;
        padding: 5px 10px;
        border-radius: 4px;
        font-size: 12px;
        z-index: 1000;
        pointer-events: none;
        transition: opacity 0.3s;
    `;
    
    document.body.appendChild(tooltip);
    
    const rect = element.getBoundingClientRect();
    tooltip.style.top = rect.top - tooltip.offsetHeight - 5 + 'px';
    tooltip.style.left = rect.left + (rect.width - tooltip.offsetWidth) / 2 + 'px';
    
    element._tooltip = tooltip;
}

function hideTooltip(event) {
    if (event.target._tooltip) {
        event.target._tooltip.remove();
        event.target._tooltip = null;
    }
}

/**
 * Initialize log viewer with auto-scroll and highlighting
 */
function initializeLogViewer() {
    const logViewer = document.querySelector('.log-viewer');
    if (!logViewer) return;
    
    // Auto-scroll to bottom for new logs
    logViewer.scrollTop = logViewer.scrollHeight;
    
    // Highlight search terms in logs
    const searchInput = document.getElementById('log-search');
    if (searchInput) {
        searchInput.addEventListener('input', function(e) {
            highlightLogEntries(e.target.value);
        });
    }
    
    // Add click handlers to log entries
    document.querySelectorAll('.log-entry').forEach(entry => {
        entry.addEventListener('click', function() {
            this.classList.toggle('expanded');
            showLogDetails(this);
        });
    });
}

function highlightLogEntries(searchTerm) {
    const logEntries = document.querySelectorAll('.log-entry');
    
    logEntries.forEach(entry => {
        const text = entry.textContent.toLowerCase();
        if (searchTerm && text.includes(searchTerm.toLowerCase())) {
            entry.style.backgroundColor = 'rgba(74, 158, 255, 0.2)';
            entry.style.borderLeft = '3px solid var(--accent-primary)';
        } else {
            entry.style.backgroundColor = '';
            entry.style.borderLeft = '';
        }
    });
}

function showLogDetails(logEntry) {
    const details = logEntry.querySelector('.log-details');
    if (details) {
        details.style.display = details.style.display === 'none' ? 'block' : 'none';
    }
}

/**
 * Initialize search and filter functionality
 */
function initializeSearchFilter() {
    const searchInput = document.getElementById('table-search');
    const filterSelect = document.getElementById('table-filter');
    
    if (searchInput) {
        searchInput.addEventListener('input', debounce(filterTable, 300));
    }
    
    if (filterSelect) {
        filterSelect.addEventListener('change', filterTable);
    }
}

function filterTable() {
    const searchInput = document.getElementById('table-search');
    const filterSelect = document.getElementById('table-filter');
    const table = document.querySelector('.data-table');
    
    if (!table) return;
    
    const searchTerm = searchInput ? searchInput.value.toLowerCase() : '';
    const filterValue = filterSelect ? filterSelect.value : '';
    const rows = table.querySelectorAll('tbody tr');
    
    rows.forEach(row => {
        let showRow = true;
        const text = row.textContent.toLowerCase();
        
        if (searchTerm && !text.includes(searchTerm)) {
            showRow = false;
        }
        
        if (filterValue && !row.classList.contains(filterValue)) {
            showRow = false;
        }
        
        row.style.display = showRow ? '' : 'none';
    });
    
    // Show/hide no results message
    const visibleRows = Array.from(rows).filter(row => row.style.display !== 'none');
    const noResultsRow = document.getElementById('no-results');
    
    if (visibleRows.length === 0) {
        if (!noResultsRow) {
            const tbody = table.querySelector('tbody');
            const tr = document.createElement('tr');
            tr.id = 'no-results';
            tr.innerHTML = '<td colspan="10" style="text-align: center; padding: 2rem;">🔍 No matching records found</td>';
            tbody.appendChild(tr);
        }
    } else if (noResultsRow) {
        noResultsRow.remove();
    }
}

/**
 * Initialize auto-refresh for real-time logs
 */
function initializeAutoRefresh() {
    const refreshToggle = document.getElementById('auto-refresh');
    if (!refreshToggle) return;
    
    let refreshInterval;
    let countdown = 30;
    
    refreshToggle.addEventListener('change', function(e) {
        if (e.target.checked) {
            startAutoRefresh();
        } else {
            stopAutoRefresh();
        }
    });
    
    function startAutoRefresh() {
        refreshInterval = setInterval(() => {
            countdown--;
            updateRefreshCountdown(countdown);
            
            if (countdown <= 0) {
                refreshData();
                countdown = 30;
            }
        }, 1000);
    }
    
    function stopAutoRefresh() {
        if (refreshInterval) {
            clearInterval(refreshInterval);
            refreshInterval = null;
        }
        updateRefreshCountdown(0);
    }
    
    function refreshData() {
        // Show loading indicator
        showLoadingIndicator();
        
        // Fetch new data
        fetch(window.location.href + '?ajax=true')
            .then(response => response.text())
            .then(html => {
                updateTableData(html);
                hideLoadingIndicator();
                showNotification('Data refreshed successfully', 'success');
            })
            .catch(error => {
                console.error('Refresh failed:', error);
                hideLoadingIndicator();
                showNotification('Failed to refresh data', 'error');
            });
    }
    
    function updateRefreshCountdown(seconds) {
        const indicator = document.getElementById('refresh-countdown');
        if (indicator) {
            if (seconds > 0) {
                indicator.textContent = `Auto-refresh in ${seconds}s`;
                indicator.style.display = 'inline';
            } else {
                indicator.style.display = 'none';
            }
        }
    }
}

/**
 * Initialize charts for analytics dashboard
 */
function initializeCharts() {
    // Activity Timeline Chart
    const timelineCanvas = document.getElementById('activity-timeline');
    if (timelineCanvas) {
        createActivityTimeline(timelineCanvas);
    }
    
    // Action Distribution Chart
    const distributionCanvas = document.getElementById('action-distribution');
    if (distributionCanvas) {
        createActionDistribution(distributionCanvas);
    }
}

function createActivityTimeline(canvas) {
    const ctx = canvas.getContext('2d');
    
    // Sample data - replace with actual data from your backend
    const data = {
        labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
        datasets: [{
            label: 'INSERT',
            data: [12, 19, 15, 17, 24, 10, 8],
            backgroundColor: 'rgba(81, 207, 102, 0.5)',
            borderColor: '#51cf66'
        }, {
            label: 'UPDATE',
            data: [8, 12, 10, 15, 18, 6, 4],
            backgroundColor: 'rgba(255, 212, 59, 0.5)',
            borderColor: '#ffd43b'
        }]
    };
    
    drawBarChart(ctx, data);
}

function createActionDistribution(canvas) {
    const ctx = canvas.getContext('2d');
    
    // Sample data
    const data = {
        labels: ['INSERT', 'UPDATE', 'DELETE', 'SELECT'],
        values: [45, 30, 15, 10],
        colors: ['#51cf66', '#ffd43b', '#ff6b6b', '#4a9eff']
    };
    
    drawPieChart(ctx, data);
}

function drawBarChart(ctx, data) {
    // Simplified bar chart drawing
    const width = ctx.canvas.width;
    const height = ctx.canvas.height;
    const padding = 40;
    
    ctx.clearRect(0, 0, width, height);
    
    // Draw axes
    ctx.beginPath();
    ctx.strokeStyle = 'var(--border-color)';
    ctx.lineWidth = 1;
    ctx.moveTo(padding, padding);
    ctx.lineTo(padding, height - padding);
    ctx.lineTo(width - padding, height - padding);
    ctx.stroke();
    
    // Draw title
    ctx.fillStyle = 'var(--text-primary)';
    ctx.font = '14px Arial';
    ctx.fillText('Activity Timeline', padding, padding - 10);
}

function drawPieChart(ctx, data) {
    const width = ctx.canvas.width;
    const height = ctx.canvas.height;
    const centerX = width / 2;
    const centerY = height / 2;
    const radius = Math.min(width, height) / 3;
    
    ctx.clearRect(0, 0, width, height);
    
    let startAngle = 0;
    data.values.forEach((value, index) => {
        const sliceAngle = (value / 100) * 2 * Math.PI;
        
        ctx.beginPath();
        ctx.fillStyle = data.colors[index];
        ctx.moveTo(centerX, centerY);
        ctx.arc(centerX, centerY, radius, startAngle, startAngle + sliceAngle);
        ctx.closePath();
        ctx.fill();
        
        // Draw label
        const labelAngle = startAngle + sliceAngle / 2;
        const labelX = centerX + Math.cos(labelAngle) * radius * 1.2;
        const labelY = centerY + Math.sin(labelAngle) * radius * 1.2;
        
        ctx.fillStyle = 'var(--text-primary)';
        ctx.font = '12px Arial';
        ctx.fillText(data.labels[index], labelX - 20, labelY - 10);
        
        startAngle += sliceAngle;
    });
}

/**
 * Initialize form validation
 */
function initializeFormValidation() {
    const forms = document.querySelectorAll('form');
    
    forms.forEach(form => {
        form.addEventListener('submit', function(e) {
            if (!validateForm(this)) {
                e.preventDefault();
            }
        });
        
        // Real-time validation
        form.querySelectorAll('input, select, textarea').forEach(field => {
            field.addEventListener('blur', function() {
                validateField(this);
            });
            
            field.addEventListener('input', function() {
                if (this.classList.contains('error')) {
                    validateField(this);
                }
            });
        });
    });
}

function validateForm(form) {
    let isValid = true;
    const fields = form.querySelectorAll('input[required], select[required], textarea[required]');
    
    fields.forEach(field => {
        if (!validateField(field)) {
            isValid = false;
        }
    });
    
    return isValid;
}

function validateField(field) {
    const value = field.value.trim();
    let isValid = true;
    let errorMessage = '';
    
    // Remove existing error
    removeFieldError(field);
    
    // Required field validation
    if (field.hasAttribute('required') && !value) {
        isValid = false;
        errorMessage = 'This field is required';
    }
    
    // Email validation
    if (field.type === 'email' && value) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(value)) {
            isValid = false;
            errorMessage = 'Please enter a valid email address';
        }
    }
    
    // Username validation
    if (field.id === 'username' && value) {
        const usernameRegex = /^[a-zA-Z0-9_]{3,20}$/;
        if (!usernameRegex.test(value)) {
            isValid = false;
            errorMessage = 'Username must be 3-20 characters (letters, numbers, underscore)';
        }
    }
    
    // Password validation
    if (field.id === 'password' && value) {
        if (value.length < 6) {
            isValid = false;
            errorMessage = 'Password must be at least 6 characters';
        }
    }
    
    if (!isValid) {
        showFieldError(field, errorMessage);
    }
    
    return isValid;
}

function showFieldError(field, message) {
    field.classList.add('error');
    field.style.borderColor = 'var(--accent-secondary)';
    
    const errorDiv = document.createElement('div');
    errorDiv.className = 'field-error';
    errorDiv.textContent = message;
    errorDiv.style.cssText = `
        color: var(--accent-secondary);
        font-size: 12px;
        margin-top: 5px;
    `;
    
    field.parentNode.appendChild(errorDiv);
}

function removeFieldError(field) {
    field.classList.remove('error');
    field.style.borderColor = '';
    
    const errorDiv = field.parentNode.querySelector('.field-error');
    if (errorDiv) {
        errorDiv.remove();
    }
}

/**
 * Initialize notifications
 */
function initializeNotifications() {
    // Check for browser notification support
    if ('Notification' in window) {
        const notifyBtn = document.getElementById('enable-notifications');
        if (notifyBtn) {
            notifyBtn.addEventListener('click', requestNotificationPermission);
        }
    }
}

function requestNotificationPermission() {
    Notification.requestPermission().then(permission => {
        if (permission === 'granted') {
            showNotification('Notifications enabled', 'success');
        }
    });
}

function showNotification(message, type = 'info') {
    // Create notification element
    const notification = document.createElement('div');
    notification.className = `notification notification-${type}`;
    notification.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 15px 20px;
        background: var(--bg-secondary);
        border-left: 4px solid ${getNotificationColor(type)};
        border-radius: 4px;
        box-shadow: var(--card-shadow);
        color: var(--text-primary);
        z-index: 9999;
        animation: slideIn 0.3s ease;
    `;
    
    notification.innerHTML = `
        <div style="display: flex; align-items: center; gap: 10px;">
            <span>${getNotificationIcon(type)}</span>
            <span>${message}</span>
        </div>
    `;
    
    document.body.appendChild(notification);
    
    // Auto-remove after 5 seconds
    setTimeout(() => {
        notification.style.animation = 'slideOut 0.3s ease';
        setTimeout(() => notification.remove(), 300);
    }, 5000);
}

function getNotificationColor(type) {
    switch(type) {
        case 'success': return 'var(--accent-success)';
        case 'error': return 'var(--accent-secondary)';
        case 'warning': return 'var(--accent-warning)';
        default: return 'var(--accent-primary)';
    }
}

function getNotificationIcon(type) {
    switch(type) {
        case 'success': return '✅';
        case 'error': return '❌';
        case 'warning': return '⚠️';
        default: return 'ℹ️';
    }
}

/**
 * Initialize loading indicators
 */
function showLoadingIndicator() {
    let loader = document.getElementById('global-loader');
    if (!loader) {
        loader = document.createElement('div');
        loader.id = 'global-loader';
        loader.style.cssText = `
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            padding: 20px;
            background: var(--bg-secondary);
            border-radius: 8px;
            box-shadow: var(--card-shadow);
            z-index: 10000;
        `;
        loader.innerHTML = '<div class="spinner"></div><p>Loading...</p>';
        document.body.appendChild(loader);
    }
    loader.style.display = 'block';
}

function hideLoadingIndicator() {
    const loader = document.getElementById('global-loader');
    if (loader) {
        loader.style.display = 'none';
    }
}

/**
 * Initialize theme toggle (optional feature)
 */
function initializeThemeToggle() {
    const themeToggle = document.getElementById('theme-toggle');
    if (!themeToggle) return;
    
    themeToggle.addEventListener('click', function() {
        document.body.classList.toggle('light-theme');
        localStorage.setItem('theme', document.body.classList.contains('light-theme') ? 'light' : 'dark');
    });
    
    // Load saved theme
    const savedTheme = localStorage.getItem('theme');
    if (savedTheme === 'light') {
        document.body.classList.add('light-theme');
    }
}

/**
 * Utility: Debounce function
 */
function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

/**
 * Export data functionality
 */
function exportData(format = 'csv') {
    const table = document.querySelector('.data-table');
    if (!table) return;
    
    let data = '';
    const rows = table.querySelectorAll('tr');
    
    rows.forEach(row => {
        const cells = row.querySelectorAll('th, td');
        const rowData = Array.from(cells).map(cell => cell.textContent.trim());
        data += rowData.join(',') + '\n';
    });
    
    // Create download link
    const blob = new Blob([data], { type: 'text/csv' });
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `export-${new Date().toISOString()}.${format}`;
    a.click();
    
    showNotification(`Data exported as ${format.toUpperCase()}`, 'success');
}

// Make functions available globally
window.exportData = exportData;
window.showNotification = showNotification;