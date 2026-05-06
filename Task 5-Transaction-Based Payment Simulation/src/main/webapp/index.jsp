<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Secure Payment System</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        
        body {
            background: linear-gradient(135deg, #1a1a2e 0%, #16213e 50%, #1a1a2e 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        
        .container {
            background: rgba(255, 255, 255, 0.05);
            backdrop-filter: blur(10px);
            border-radius: 20px;
            padding: 40px;
            box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.37);
            border: 1px solid rgba(255, 255, 255, 0.1);
            width: 90%;
            max-width: 500px;
        }
        
        h1 {
            color: #4ecdc4;
            text-align: center;
            margin-bottom: 30px;
            font-size: 2.5em;
            text-shadow: 0 0 10px rgba(78, 205, 196, 0.3);
        }
        
        .subtitle {
            color: #a8dadc;
            text-align: center;
            margin-bottom: 30px;
            font-size: 1.1em;
        }
        
        .features {
            list-style: none;
            margin: 30px 0;
        }
        
        .features li {
            color: #f1faee;
            padding: 10px 0;
            border-bottom: 1px solid rgba(255, 255, 255, 0.1);
            display: flex;
            align-items: center;
        }
        
        .features li:before {
            content: "✓";
            color: #4ecdc4;
            font-weight: bold;
            margin-right: 10px;
        }
        
        .btn {
            display: block;
            width: 100%;
            padding: 15px;
            background: linear-gradient(135deg, #4ecdc4 0%, #2c7a7b 100%);
            color: white;
            text-align: center;
            text-decoration: none;
            border: none;
            border-radius: 10px;
            font-size: 1.2em;
            font-weight: bold;
            cursor: pointer;
            transition: all 0.3s ease;
            border: 1px solid rgba(255, 255, 255, 0.2);
        }
        
        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 20px rgba(78, 205, 196, 0.4);
        }
        
        .stats {
            display: flex;
            justify-content: space-around;
            margin-top: 30px;
            color: #f1faee;
        }
        
        .stat-item {
            text-align: center;
        }
        
        .stat-value {
            font-size: 1.5em;
            color: #4ecdc4;
            font-weight: bold;
        }
        
        .stat-label {
            font-size: 0.9em;
            color: #a8dadc;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>🔐 SecurePay</h1>
        <div class="subtitle">Bank-Grade Transaction Processing System</div>
        
        <ul class="features">
            <li>Real-time balance deduction from user account</li>
            <li>Instant credit to merchant account</li>
            <li>ACID-compliant transactions with COMMIT/ROLLBACK</li>
            <li>End-to-end encryption simulation</li>
            <li>Automatic failure recovery</li>
        </ul>
        
        <a href="payment" class="btn">Start Payment Simulation</a>
        
        <div class="stats">
            <div class="stat-item">
                <div class="stat-value">100%</div>
                <div class="stat-label">Uptime</div>
            </div>
            <div class="stat-item">
                <div class="stat-value">24/7</div>
                <div class="stat-label">Support</div>
            </div>
            <div class="stat-item">
                <div class="stat-value">🔒</div>
                <div class="stat-label">Secure</div>
            </div>
        </div>
    </div>
</body>
</html>