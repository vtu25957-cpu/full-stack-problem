<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.dashboard.model.Student, com.dashboard.dao.StudentDAO" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Records Dashboard</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
        }

        body {
            background: linear-gradient(135deg, #0a0a0a 0%, #1a0000 50%, #0a0a0a 100%);
            min-height: 100vh;
            padding: 20px;
            position: relative;
        }

        /* Subtle gradient overlay */
        body::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: radial-gradient(circle at 50% 50%, rgba(0, 255, 255, 0.03) 0%, transparent 70%);
            pointer-events: none;
        }

        .dashboard-container {
            max-width: 1400px;
            margin: 0 auto;
            background: rgba(10, 10, 10, 0.95);
            backdrop-filter: blur(10px);
            border-radius: 24px;
            box-shadow: 0 25px 50px -12px rgba(255, 0, 0, 0.25);
            overflow: hidden;
            position: relative;
            z-index: 1;
            border: 1px solid rgba(255, 0, 0, 0.2);
        }

        /* Header with red gradient */
        .dashboard-header {
            background: linear-gradient(145deg, #100000, #200000);
            color: #ffffff;
            padding: 32px 40px;
            border-bottom: 2px solid #ff0000;
            position: relative;
        }

        .dashboard-header::after {
            content: '';
            position: absolute;
            bottom: -2px;
            left: 0;
            right: 0;
            height: 1px;
            background: linear-gradient(90deg, transparent, #00ffff, #ffd700, #ff0000, transparent);
        }

        .dashboard-header h1 {
            font-size: 2.5em;
            margin-bottom: 8px;
            font-weight: 600;
            letter-spacing: -0.5px;
        }

        .dashboard-header h1 i {
            margin-right: 12px;
            color: #ff0000;
        }

        .header-subtitle {
            color: #a0a0a0;
            font-size: 1rem;
            font-weight: 400;
        }

        /* Stats Cards */
        .stats-container {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
            gap: 20px;
            padding: 32px;
        }

        .stat-card {
            background: linear-gradient(145deg, #111111, #050505);
            border-radius: 20px;
            padding: 24px;
            display: flex;
            align-items: center;
            border: 1px solid #330000;
            transition: all 0.3s ease;
            position: relative;
            overflow: hidden;
        }

        .stat-card::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 2px;
            background: linear-gradient(90deg, #ff0000, #00ffff, #ffd700);
            transform: translateX(-100%);
            transition: transform 0.5s ease;
        }

        .stat-card:hover {
            transform: translateY(-4px);
            border-color: #ff0000;
            box-shadow: 0 10px 30px -10px rgba(255, 0, 0, 0.3);
        }

        .stat-card:hover::before {
            transform: translateX(100%);
        }

        .stat-icon {
            width: 60px;
            height: 60px;
            background: linear-gradient(135deg, #ff0000, #990000);
            border-radius: 16px;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-right: 20px;
            font-size: 1.8em;
            color: #ffffff;
            box-shadow: 0 5px 15px rgba(255, 0, 0, 0.2);
        }

        .stat-info h3 {
            font-size: 0.9rem;
            color: #a0a0a0;
            margin-bottom: 6px;
            font-weight: 500;
            letter-spacing: 0.5px;
        }

        .stat-info p {
            font-size: 2.2em;
            font-weight: 700;
            color: #00ffff;
            text-shadow: 0 0 20px rgba(0, 255, 255, 0.3);
        }

        /* Controls Section */
        .controls-section {
            padding: 24px 32px;
            background: #0a0a0a;
            border-top: 1px solid #220000;
            border-bottom: 1px solid #220000;
        }

        .search-box {
            position: relative;
            margin-bottom: 20px;
        }

        .search-box i {
            position: absolute;
            left: 16px;
            top: 50%;
            transform: translateY(-50%);
            color: #ff0000;
            font-size: 1.1em;
        }

        .search-box input {
            width: 100%;
            padding: 14px 20px 14px 45px;
            border: 1px solid #330000;
            border-radius: 12px;
            font-size: 0.95em;
            background: #111111;
            color: #ffffff;
            transition: all 0.3s ease;
        }

        .search-box input:focus {
            outline: none;
            border-color: #ff0000;
            box-shadow: 0 0 0 3px rgba(255, 0, 0, 0.1);
            background: #161616;
        }

        .search-box input::placeholder {
            color: #444444;
        }

        .filter-sort-container {
            display: flex;
            flex-wrap: wrap;
            gap: 16px;
            align-items: center;
        }

        .filter-group, .sort-group {
            display: flex;
            align-items: center;
            gap: 12px;
            background: #111111;
            padding: 8px 16px;
            border-radius: 12px;
            border: 1px solid #330000;
            transition: all 0.3s ease;
        }

        .filter-group:hover, .sort-group:hover {
            border-color: #ff0000;
        }

        .filter-group label, .sort-group label {
            color: #ffd700;
            font-size: 0.9rem;
            font-weight: 500;
        }

        .filter-group select, .sort-group select {
            padding: 8px 12px;
            border: none;
            border-radius: 8px;
            background: #1a1a1a;
            color: #00ffff;
            cursor: pointer;
            font-size: 0.9em;
            transition: all 0.3s ease;
        }

        .filter-group select:hover, .sort-group select:hover {
            background: #222222;
        }

        .sort-order-btn {
            padding: 8px 16px;
            background: #ff0000;
            color: #ffffff;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            transition: all 0.3s ease;
            font-size: 1em;
        }

        .sort-order-btn:hover {
            background: #cc0000;
            transform: scale(1.05);
        }

        /* Department Statistics Section */
        .dept-stats-section {
            padding: 32px;
            background: linear-gradient(180deg, #0a0a0a, #050505);
        }

        .dept-stats-section h2 {
            color: #ffffff;
            margin-bottom: 24px;
            font-size: 1.5rem;
            font-weight: 600;
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .dept-stats-section h2 i {
            color: #ff0000;
        }

        .dept-cards-container {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
        }

        .dept-card {
            background: #111111;
            border-radius: 16px;
            padding: 20px;
            text-align: center;
            border: 1px solid #330000;
            transition: all 0.3s ease;
        }

        .dept-card:hover {
            border-color: #00ffff;
            transform: translateY(-4px);
            box-shadow: 0 10px 30px -10px rgba(0, 255, 255, 0.2);
        }

        .dept-card h3 {
            color: #ffd700;
            margin-bottom: 12px;
            font-size: 1.1rem;
            font-weight: 600;
        }

        .dept-count {
            font-size: 2.5rem;
            font-weight: 700;
            color: #00ffff;
            margin-bottom: 12px;
        }

        .dept-progress {
            width: 100%;
            height: 6px;
            background: #222222;
            border-radius: 3px;
            overflow: hidden;
        }

        .progress-bar {
            height: 100%;
            background: linear-gradient(90deg, #ff0000, #00ffff);
            border-radius: 3px;
            transition: width 0.5s ease;
        }

        /* Table Section */
        .table-section {
            padding: 32px;
            background: #0a0a0a;
        }

        .table-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 24px;
        }

        .table-header h2 {
            color: #ffffff;
            font-size: 1.5rem;
            font-weight: 600;
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .table-header h2 i {
            color: #ff0000;
        }

        .record-count {
            background: #111111;
            color: #ffd700;
            padding: 8px 20px;
            border-radius: 30px;
            font-size: 0.9rem;
            border: 1px solid #330000;
        }

        .table-wrapper {
            overflow-x: auto;
            border-radius: 16px;
            border: 1px solid #220000;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background: #111111;
        }

        thead {
            background: #1a1a1a;
            border-bottom: 2px solid #ff0000;
        }

        th {
            padding: 16px 20px;
            text-align: left;
            font-weight: 600;
            color: #ffffff;
            font-size: 0.9rem;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        th:hover {
            color: #ff0000;
        }

        td {
            padding: 14px 20px;
            border-bottom: 1px solid #222222;
            color: #e0e0e0;
        }

        tbody tr:hover {
            background: #1a1a1a;
        }

        tbody tr:hover td {
            color: #00ffff;
        }

        td:first-child {
            color: #ffd700;
            font-weight: 600;
        }

        .status-badge {
            padding: 4px 12px;
            border-radius: 30px;
            font-size: 0.85rem;
            font-weight: 500;
            display: inline-block;
        }

        .status-active {
            background: rgba(0, 255, 255, 0.1);
            color: #00ffff;
            border: 1px solid rgba(0, 255, 255, 0.3);
        }

        .status-inactive {
            background: rgba(255, 0, 0, 0.1);
            color: #ff0000;
            border: 1px solid rgba(255, 0, 0, 0.3);
        }

        /* Custom Scrollbar */
        ::-webkit-scrollbar {
            width: 10px;
            height: 10px;
        }

        ::-webkit-scrollbar-track {
            background: #1a1a1a;
        }

        ::-webkit-scrollbar-thumb {
            background: #ff0000;
            border-radius: 5px;
        }

        ::-webkit-scrollbar-thumb:hover {
            background: #00ffff;
        }

        /* Responsive */
        @media (max-width: 768px) {
            .dashboard-header h1 {
                font-size: 1.8em;
            }
            
            .stats-container {
                grid-template-columns: 1fr;
                padding: 20px;
            }
            
            .filter-sort-container {
                flex-direction: column;
                align-items: stretch;
            }
            
            .filter-group, .sort-group {
                justify-content: space-between;
            }
        }
    </style>
</head>
<body>
    <%
        StudentDAO dao = new StudentDAO();
        List<Student> students = dao.getAllStudents();
        Map<String, Integer> deptCounts = dao.getDepartmentCounts();
        int totalStudents = dao.getTotalStudents();
    %>
    
    <div class="dashboard-container">
        <header class="dashboard-header">
            <h1><i class="fas fa-graduation-cap"></i> Student Records Dashboard</h1>
            <p class="header-subtitle">Manage and track student information efficiently</p>
        </header>

        <div class="stats-container">
            <div class="stat-card">
                <div class="stat-icon"><i class="fas fa-users"></i></div>
                <div class="stat-info">
                    <h3>Total Students</h3>
                    <p><%= totalStudents %></p>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-icon"><i class="fas fa-building"></i></div>
                <div class="stat-info">
                    <h3>Total Departments</h3>
                    <p><%= deptCounts.size() %></p>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-icon"><i class="fas fa-calendar"></i></div>
                <div class="stat-info">
                    <h3>Latest Entry</h3>
                    <p>2024</p>
                </div>
            </div>
        </div>

        <div class="controls-section">
            <div class="search-box">
                <i class="fas fa-search"></i>
                <input type="text" id="searchInput" placeholder="Search students...">
            </div>

            <div class="filter-sort-container">
                <div class="filter-group">
                    <label><i class="fas fa-filter"></i> Department:</label>
                    <select id="departmentFilter">
                        <option value="all">All Departments</option>
                        <% for(String dept : deptCounts.keySet()) { %>
                            <option value="<%= dept %>"><%= dept %></option>
                        <% } %>
                    </select>
                </div>
                
                <div class="sort-group">
                    <label><i class="fas fa-sort"></i> Sort by:</label>
                    <select id="sortBy">
                        <option value="name">Name</option>
                        <option value="date">Date</option>
                        <option value="department">Department</option>
                        <option value="grade">Grade</option>
                    </select>
                    <button id="sortOrderBtn" class="sort-order-btn">
                        <i class="fas fa-arrow-up"></i>
                    </button>
                </div>
            </div>
        </div>

        <div class="dept-stats-section">
            <h2><i class="fas fa-chart-pie"></i> Department Statistics</h2>
            <div class="dept-cards-container">
                <% for(Map.Entry<String, Integer> entry : deptCounts.entrySet()) { 
                    int percentage = (entry.getValue() * 100 / totalStudents); %>
                    <div class="dept-card">
                        <h3><%= entry.getKey() %></h3>
                        <div class="dept-count"><%= entry.getValue() %></div>
                        <div class="dept-progress">
                            <div class="progress-bar" style="width: <%= percentage %>%"></div>
                        </div>
                    </div>
                <% } %>
            </div>
        </div>

        <div class="table-section">
            <div class="table-header">
                <h2><i class="fas fa-list"></i> Student Records</h2>
                <span class="record-count" id="recordCount">Showing <%= students.size() %> records</span>
            </div>
            <div class="table-wrapper">
                <table id="recordsTable">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name <i class="fas fa-sort" data-sort="name"></i></th>
                            <th>Department</th>
                            <th>Grade</th>
                            <th>Join Date <i class="fas fa-sort" data-sort="date"></i></th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody id="tableBody">
                        <% for(Student student : students) { %>
                            <tr>
                                <td>#<%= student.getId() %></td>
                                <td><%= student.getName() %></td>
                                <td><%= student.getDepartment() %></td>
                                <td><%= student.getGrade() %></td>
                                <td><%= student.getJoinDate() %></td>
                                <td>
                                    <span class="status-badge <%= student.getStatus().equals("active") ? "status-active" : "status-inactive" %>">
                                        <%= student.getStatus().substring(0,1).toUpperCase() + student.getStatus().substring(1) %>
                                    </span>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const searchInput = document.getElementById('searchInput');
            const filterSelect = document.getElementById('departmentFilter');
            const sortSelect = document.getElementById('sortBy');
            const sortOrderBtn = document.getElementById('sortOrderBtn');
            const tableBody = document.getElementById('tableBody');
            const recordCount = document.getElementById('recordCount');
            
            let currentSortOrder = 'asc';
            let originalRows = Array.from(tableBody.getElementsByTagName('tr'));
            
            sortOrderBtn.addEventListener('click', function() {
                currentSortOrder = currentSortOrder === 'asc' ? 'desc' : 'asc';
                this.innerHTML = currentSortOrder === 'asc' ? 
                    '<i class="fas fa-arrow-up"></i>' : 
                    '<i class="fas fa-arrow-down"></i>';
                filterAndSortTable();
            });
            
            searchInput.addEventListener('keyup', filterAndSortTable);
            filterSelect.addEventListener('change', filterAndSortTable);
            sortSelect.addEventListener('change', filterAndSortTable);
            
            function filterAndSortTable() {
                const searchTerm = searchInput.value.toLowerCase();
                const filterDept = filterSelect.value;
                const sortBy = sortSelect.value;
                
                let filteredRows = originalRows.filter(row => {
                    const name = row.cells[1].textContent.toLowerCase();
                    const dept = row.cells[2].textContent;
                    const grade = row.cells[3].textContent.toLowerCase();
                    
                    const matchesSearch = name.includes(searchTerm) || 
                                         dept.toLowerCase().includes(searchTerm) ||
                                         grade.includes(searchTerm);
                    const matchesFilter = filterDept === 'all' || dept === filterDept;
                    
                    return matchesSearch && matchesFilter;
                });
                
                filteredRows.sort((a, b) => {
                    let aVal, bVal;
                    
                    switch(sortBy) {
                        case 'name':
                            aVal = a.cells[1].textContent;
                            bVal = b.cells[1].textContent;
                            break;
                        case 'date':
                            aVal = a.cells[4].textContent;
                            bVal = b.cells[4].textContent;
                            break;
                        case 'department':
                            aVal = a.cells[2].textContent;
                            bVal = b.cells[2].textContent;
                            break;
                        case 'grade':
                            aVal = a.cells[3].textContent;
                            bVal = b.cells[3].textContent;
                            break;
                        default:
                            return 0;
                    }
                    
                    if (currentSortOrder === 'asc') {
                        return aVal.localeCompare(bVal);
                    } else {
                        return bVal.localeCompare(aVal);
                    }
                });
                
                tableBody.innerHTML = '';
                filteredRows.forEach(row => tableBody.appendChild(row));
                recordCount.textContent = `Showing ${filteredRows.length} of <%= students.size() %> records`;
            }
        });
    </script>
</body>
</html>