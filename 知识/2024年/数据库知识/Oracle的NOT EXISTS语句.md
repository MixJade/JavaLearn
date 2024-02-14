# Oracle的NOT EXISTS语句

Oracle中的NOT EXISTS用于执行子查询，如果子查询没有返回任任行，则返回TRUE。它通常用于对某些填充条件进行过滤。

在SQL中，您可以使用子查询创建一个临时表，您可以使用NOT EXISTS，EXISTS或JOIN这个临时表来过滤或者提取出需要的数据。

这是一些例子：

例1：获取在“orders”表中但不在“order_details”表中的所有订单。

```sql
SELECT order_id 
FROM orders
WHERE NOT EXISTS (
    SELECT 1 
    FROM order_details 
    WHERE order_details.order_id = orders.order_id
)
```
这个查询返回的是所有在"orders"表中但没有任何详细信息在"order_details"表中的订单。

例2：获取在“employees”表中，但是不在“sales”表中的所有员工。

```sql
SELECT employee_id 
FROM employees
WHERE NOT EXISTS (
    SELECT 1 
    FROM sales 
    WHERE sales.employee_id = employees.employee_id
)
```
这个查询返回的是所有在"employees"表中但没有在"sales"表中出现过的员工。