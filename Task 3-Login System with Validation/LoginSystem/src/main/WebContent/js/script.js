// Simple working login script with localStorage support
console.log("Script loaded successfully!");

// Wait for page to load completely
window.onload = function() {
    console.log("Page loaded!");
    
    // Get elements
    var loginForm = document.getElementById('loginForm');
    var username = document.getElementById('username');
    var password = document.getElementById('password');
    var errorMsg = document.getElementById('errorMessage');
    var errorSpan = errorMsg.querySelector('span');
    var loginBtn = document.getElementById('loginBtn');
    
    // Check if elements exist
    console.log("Form found:", loginForm);
    console.log("Username field found:", username);
    console.log("Password field found:", password);
    
    if(!loginForm || !username || !password) {
        console.error("Some elements are missing!");
        return;
    }
    
    // Get users from localStorage (from signup)
    var storedUsers = {};
    try {
        storedUsers = JSON.parse(localStorage.getItem('usersDB') || '{}');
        console.log("Stored users found:", Object.keys(storedUsers).length);
    } catch(e) {
        console.log("No stored users yet");
    }
    
    // Default users (hardcoded)
    var usersDB = {
        "admin": { password: "admin123", name: "Administrator" },
        "john_doe": { password: "john2024", name: "John Doe" },
        "jane_smith": { password: "jane456", name: "Jane Smith" },
        "testuser": { password: "test123", name: "Test User" }
    };
    
    // Merge with stored users (users from signup page)
    for(var key in storedUsers) {
        usersDB[key] = storedUsers[key];
    }
    
    console.log("Total users available:", Object.keys(usersDB).length);
    
    // Toggle password function (make it global)
    window.togglePassword = function() {
        console.log("Toggle password clicked");
        if(password.type === "password") {
            password.type = "text";
            document.querySelector('.password-toggle i').className = "fas fa-eye-slash";
        } else {
            password.type = "password";
            document.querySelector('.password-toggle i').className = "fas fa-eye";
        }
    };
    
    // Handle form submit
    loginForm.onsubmit = function(event) {
        event.preventDefault();
        console.log("Form submitted");
        
        var userValue = username.value.trim();
        var passValue = password.value.trim();
        var loginSuccessful = false;
        var userName = "";
        
        // Simple validation
        if(userValue === "" || passValue === "") {
            errorMsg.style.display = "flex";
            errorSpan.innerHTML = "Both fields are required!";
            return;
        }
        
        if(passValue.length < 6) {
            errorMsg.style.display = "flex";
            errorSpan.innerHTML = "Password must be at least 6 characters!";
            return;
        }
        
        // Check credentials against usersDB
        for(var dbUser in usersDB) {
            if(dbUser.toLowerCase() === userValue.toLowerCase()) {
                if(usersDB[dbUser].password === passValue) {
                    loginSuccessful = true;
                    userName = usersDB[dbUser].name || dbUser;
                    break;
                }
            }
        }
        
        // Handle login result
        if(loginSuccessful) {
            errorMsg.style.background = "rgba(46, 213, 115, 0.2)";
            errorMsg.style.border = "1px solid rgba(46, 213, 115, 0.3)";
            errorMsg.style.color = "#2ed573";
            errorMsg.style.display = "flex";
            errorSpan.innerHTML = "Login successful! Welcome " + userName + "!";
            
            // Store current user in session
            sessionStorage.setItem('currentUser', JSON.stringify({
                username: userValue,
                name: userName
            }));
            
            setTimeout(function() {
                alert("Welcome to Dashboard, " + userName + "!");
                // You can redirect to dashboard here
                // window.location.href = "dashboard.html";
            }, 1000);
        } 
        else {
            errorMsg.style.background = "rgba(255, 71, 87, 0.2)";
            errorMsg.style.border = "1px solid rgba(255, 71, 87, 0.3)";
            errorMsg.style.color = "#ff4757";
            errorMsg.style.display = "flex";
            errorSpan.innerHTML = "Invalid username or password!";
            password.value = "";
        }
    };
    
    // Check for remembered user
    try {
        var rememberedUser = localStorage.getItem('rememberedUser');
        if(rememberedUser) {
            var userData = JSON.parse(rememberedUser);
            username.value = userData.username;
            document.getElementById('remember').checked = true;
        }
    } catch(e) {
        console.log("No remembered user");
    }
    
    // Hide error when typing
    username.oninput = function() {
        errorMsg.style.display = "none";
    };
    
    password.oninput = function() {
        errorMsg.style.display = "none";
    };
};