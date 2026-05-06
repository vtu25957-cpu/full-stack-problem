// Simple validation
document.getElementById('registrationForm').onsubmit = function() {
    var name = document.querySelector('[name="name"]').value;
    var email = document.querySelector('[name="email"]').value;
    var phone = document.querySelector('[name="phone"]').value;
    
    if(name.length < 3) {
        alert('Name must be at least 3 characters');
        return false;
    }
    
    if(!email.includes('@') || !email.includes('.')) {
        alert('Enter valid email');
        return false;
    }
    
    var phoneRegex = /^\d{3}-\d{3}-\d{4}$/;
    if(!phoneRegex.test(phone)) {
        alert('Phone must be 123-456-7890');
        return false;
    }
    
    return true;
};

// Auto-format phone
document.querySelector('[name="phone"]').oninput = function(e) {
    let x = e.target.value.replace(/\D/g, '').match(/(\d{0,3})(\d{0,3})(\d{0,4})/);
    e.target.value = !x[2] ? x[1] : x[1] + '-' + x[2] + (x[3] ? '-' + x[3] : '');
};