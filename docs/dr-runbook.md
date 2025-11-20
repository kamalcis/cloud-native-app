# This is a DR Runbook - PURE TEXT:

EMERGENCY DATABASE FAILOVER PROCEDURE

1. 🚨 IDENTIFY ISSUE
   - Check Azure Portal → SQL Database → Metrics
   - Look for "DTU Percentage > 95%"
   - Check "Failed Connections" graph

2. 👥 NOTIFY PEOPLE
   - Call Database Team: +1-555-0123 (XXX)
   - Page Platform Team: pagerduty-platform
   - teams: #teams-id

3. ⚡ TAKE ACTION  
   - Login to Azure Portal
   - Navigate to SQL Server → Failover Groups
   - Click "Failover" button
   - Wait 2 minutes for completion

4. ✅ VERIFY RECOVERY
   - Check application health: https://status.company.com
   - Verify new database connections
   - Confirm data consistency