db.product.drop();

use("GadgeStore");

db.createCollection("product", {
    validator: {
        $jsonSchema: {
            bsonType: "object",
            required: ["name", "price", "inStock"],
            properties: {
                name: {
                    bsonType: "string",
                    description:"Must be a string and is required"
                },
                price: {
                    bsonType: ["int", "double"],
                    description: "Must be either a int or double and is required",
                },
                inStock: {
                    bsonType: "bool",
                    description: "Must be a boolean and is required",
                }

            }
        }
    },
    validationAction: "error"
});

db.product.insertMany ({
    name: "Mouse",
    price: 29.99,
    inStock: true
},
{
    name: "Laptop",
    price: 29.99,
    inStock: true
},
{
    name: "Keyboard",
    price: 59.99,
    inStock: true
});

db.product.insertOne ({
    name: "Gaming Laptop",
    price: 1299.99,
    inStock: false,
    amount: 20
});

