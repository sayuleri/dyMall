2025年2月27日

存在问题：

total_price 和 order_items 的 price 都是 0.00，说明：

- 订单总价 total_price 没有正确计算并存入 orders 表。
- 订单项 price 没有正确存入 order_items 表。

---

2025年2月28日

已解决：

- 订单总价 total_price 没有正确计算并存入 orders 表。
- 订单项 price 没有正确存入 order_items 表。

->创建订单✅

存在问题：

- 修改订单信息❎
- 订单定时取消❎
