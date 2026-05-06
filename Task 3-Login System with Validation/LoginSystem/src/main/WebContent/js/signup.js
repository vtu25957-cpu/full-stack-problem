// Signup page script
console.log("Signup script loaded!");

window.onload = function() {
    console.log("Signup page loaded!");
    
    // Get elements
    var signupForm = document.getElementById('signupForm');
    var firstName = document.getElementById('firstName');
    var lastName = document.getElementById('lastName');
    var username = document.getElementById('username');
    var email = document.getElementById('email');
    var password = document.getElementById('password');
    var confirmPassword = document.getElementById('confirmPassword');
    var terms = document.getElementById('terms');
    var errorMsg = document.getElementById('errorMessage');
    var errorSpan = errorMsg.querySelector('span');
    var successMsg = document.getElementById('successMessage');
    var successSpan = successMsg.querySelector('span');
    var signupBtn = document.getElementById('signupBtn');
    var strengthBar = document.getElementById('strengthBar');
    var strengthText = document.getElementById('strengthText');
    
    if(!signupForm) {
        console.error("Signup form not found!");
        return;
    }
    
    // Toggle password function
    window.togglePassword = function(fieldId) {
        var field = document.getElementById(fieldId);
        var icon = event.target;
        
        if(field.type === "password") {
            field.type = "text";
            icon.className = "fas fa-eye-slash";
        } else {
            field.type = "password";
            icon.className = "fas fa-eye";
        }
    };
    
    // Password strength checker
    function checkPasswordStrength(pass) {
        var strength = 0;
        
        if(pass.length >= 6) strength += 20;
        if(pass.length >= 8) strength += 10;
        if(/[a-z]/.test(pass)) strength += 20;
        if(/[A-Z]/.test(pass)) strength += 20;
        if(/[0-9]/.test(pass)) strength += 15;
        if(/[^a-zA-Z0-9]/.test(pass)) strength += 15;
        
        return Math.min(strength, 100);
    }
    
    // Update password strength
    if(password) {
        password.addEventListener('input', function() {
            var strength = checkPasswordStrength(this.value);
            if(strengthBar) {
                strengthBar.style.width = strength + '%';
                
                if(strength < 30) {
                    strengthBar.style.background = '#ff4757';
                    if(strengthText) strengthText.textContent = 'Weak password';
                } else if(strength < 60) {
                    strengthBar.style.background = '#ffa502';
                    if(strengthText) strengthText.textContent = 'Medium password';
                } else if(strength < 80) {
                    strengthBar.style.background = '#2ed573';
                    if(strengthText) strengthText.textContent = 'Good password';
                } else {
                    strengthBar.style.background = '#00d2d3';
                    if(strengthText) strengthText.textContent = 'Strong password';
                }
                
                if(this.value.length === 0) {
                    strengthBar.style.width = '0%';
                    if(strengthText) strengthText.textContent = 'Enter password';
                }
            }
        });
    }
    
    // Email validation
    function isValidEmail(email) {
        var re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return re.test(email);
    }
    
    // Check if username exists
    function usernameExists(username) {
        // Check in localStorage
        var users = JSON.parse(localStorage.getItem('usersDB') || '{}');
        return users[username] !== undefined;
    }
    
    // Handle form submit
    if(signupForm) {
        signupForm.onsubmit = function(event) {
            event.preventDefault();
            console.log("Signup form submitted");
            
            // Hide previous messages
            if(errorMsg) errorMsg.style.display = 'none';
            if(successMsg) successMsg.style.display = 'none';
            
            // Get values
            var first = firstName ? firstName.value.trim() : '';
            var last = lastName ? lastName.value.trim() : '';
            var user = username ? username.value.trim() : '';
            var mail = email ? email.value.trim() : '';
            var pass = password ? password.value : '';
            var confirm = confirmPassword ? confirmPassword.value : '';
            
            // Validation
            if(!first || !last || !user || !mail || !pass || !confirm) {
                if(errorMsg) {
                    errorMsg.style.display = 'flex';
                    errorSpan.innerHTML = 'All fields are required!';
                }
                return;
            }
            
            if(first.length < 2) {
                errorMsg.style.display = 'flex';
                errorSpan.innerHTML = 'First name must be at least 2 characters!';
                return;
            }
            
            if(user.length < 3) {
                errorMsg.style.display = 'flex';
                errorSpan.innerHTML = 'Username must be at least 3 characters!';
                return;
            }
            
            if(!isValidEmail(mail)) {
                errorMsg.style.display = 'flex';
                errorSpan.innerHTML = 'Please enter a valid email address!';
                return;
            }
            
            if(pass.length < 6) {
                errorMsg.style.display = 'flex';
                errorSpan.innerHTML = 'Password must be at least 6 characters!';
                return;
            }
            
            if(pass !== confirm) {
                errorMsg.style.display = 'flex';
                errorSpan.innerHTML = 'Passwords do not match!';
                return;
            }
            
            if(terms && !terms.checked) {
                errorMsg.style.display = 'flex';
                errorSpan.innerHTML = 'Please accept Terms and Conditions!';
                return;
            }
            
            if(usernameExists(user)) {
                errorMsg.style.display = 'flex';
                errorSpan.innerHTML = 'Username already exists!';
                return;
            }
            
            // Show loading state
            if(signupBtn) {
                signupBtn.classList.add('loading');
                signupBtn.disabled = true;
            }
            
            // Simulate API call
            setTimeout(function() {
                // Save user to localStorage
                var usersDB = JSON.parse(localStorage.getItem('usersDB') || '{}');
                usersDB[user] = {
                    password: pass,
                    name: first + ' ' + last,
                    email: mail,
                    firstName: first,
                    lastName: last
                };
                localStorage.setItem('usersDB', JSON.stringify(usersDB));
                
                // Also save in users array for other purposes
                var users = JSON.parse(localStorage.getItem('users') || '[]');
                users.push({
                    firstName: first,
                    lastName: last,
                    username: user,
                    email: mail,
                    password: pass
                });
                localStorage.setItem('users', JSON.stringify(users));
                
                // Remove loading state
                if(signupBtn) {
                    signupBtn.classList.remove('loading');
                    signupBtn.disabled = false;
                }
                
                // Show success message
                if(successMsg) {
                    successMsg.style.display = 'flex';
                    successSpan.innerHTML = 'Account created successfully! Redirecting to login...';
                }
                
                // Clear form
                if(signupForm) signupForm.reset();
                if(strengthBar) strengthBar.style.width = '0%';
                if(strengthText) strengthText.textContent = 'Enter password';
                
                // Redirect to login after 2 seconds
                setTimeout(function() {
                    window.location.href = 'index.html';
                }, 2000);
                
            }, 1500);
        };
    }
    
    // Hide error when typing
    var inputs = [firstName, lastName, username, email, password, confirmPassword];
    for(var i = 0; i < inputs.length; i++) {
        if(inputs[i]) {
            inputs[i].addEventListener('input', function() {
                if(errorMsg) errorMsg.style.display = 'none';
            });
        }
    }
};