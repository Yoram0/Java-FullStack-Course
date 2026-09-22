
// Switch/use database (adjust database name as needed)
db = db.getSiblingDB("store_db");

// ==========================================
// 0. Setup: Ensure a sample product exists
// ==========================================
// Check if a sample product exists, or create one for demonstration
let sampleProduct = db.products.findOne({ "specs.brand": "Logitech" });

if (!sampleProduct) {
  const result = db.products.insertOne({
    name: "Wireless Mouse M310",
    price: 30,
    specs: {
      brand: "Logitech",
      color: "Black"
    }
  });
  sampleProduct = { _id: result.insertedId };
  print(`Created sample product with _id: ${sampleProduct._id}`);
}

const targetId = sampleProduct._id;

// ==========================================
// 1. Update: Add top-level category field ($set)
// ==========================================
db.products.updateOne(
  { _id: targetId },
  { $set: { category: "Accessories" } }
);
print("Added category: 'Accessories'");

// ==========================================
// 2. Update: Increase price by $15 ($inc)
// ==========================================
db.products.updateOne(
  { _id: targetId },
  { $inc: { price: 15 } }
);
print("Increased price by 15");

// ==========================================
// 3. Update: Push 'wireless', then push 'bestseller' ($push)
// ==========================================
// Push 'wireless'
db.products.updateOne(
  { _id: targetId },
  { $push: { tags: "wireless" } }
);

// Push 'bestseller'
db.products.updateOne(
  { _id: targetId },
  { $push: { tags: "bestseller" } }
);
print("Pushed 'wireless' and 'bestseller' to tags array");

// ==========================================
// 4. Update: Remove 'wireless' from tags ($pull)
// ==========================================
db.products.updateOne(
  { _id: targetId },
  { $pull: { tags: "wireless" } }
);
print("Removed 'wireless' from tags array");

// ==========================================
// 5. Query: Find products priced >= certain amount ($gte)
// ==========================================
print("\n--- Products with price >= 40 ---");
const gteResults = db.products.find({ price: { $gte: 40 } }).toArray();
printjson(gteResults);

// ==========================================
// 6. Query: Find products by brand using dot notation
// ==========================================
print("\n--- Products with specs.brand = 'Logitech' ---");
const brandResults = db.products.find({ "specs.brand": "Logitech" }).toArray();
printjson(brandResults);

// ==========================================
// 7. Query: Find products with category in a list ($in)
// ==========================================
print("\n--- Products in category ['Accessories', 'Electronics'] ---");
const inResults = db.products.find({
  category: { $in: ["Accessories", "Electronics"] }
}).toArray();
printjson(inResults);

// ==========================================
// 8. Collection: Create second collection named 'orders'
// ==========================================
if (!db.getCollectionNames().includes("orders")) {
  db.createCollection("orders");
  print("\nCreated collection 'orders'");
}

// ==========================================
// 9. Insert: Order document linking product _id
// ==========================================
const orderResult = db.orders.insertOne({
  productId: targetId,
  quantity: 2
});
print(`Inserted order with _id: ${orderResult.insertedId}`);

// ==========================================
// 10 & 11. Aggregation Pipeline: Lookup, Unwind, and Project Receipt
// ==========================================
print("\n--- Clean Customer Receipt (Aggregation Output) ---");
const receipts = db.orders.aggregate([
  // 1. Join orders with products
  {
    $lookup: {
      from: "products",
      localField: "productId",
      foreignField: "_id",
      as: "product"
    }
  },
  // 2. Deconstruct product array to single object
  {
    $unwind: "$product"
  },
  // 3. Output clean receipt format
  {
    $project: {
      _id: 0,
      productName: "$product.name",
      quantity: "$quantity"
    }
  }
]).toArray();

printjson(receipts);