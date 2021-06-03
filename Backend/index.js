//const functions = require('firebase-functions');

const express = require("express");
const app = express();
const { resolve } = require("path");
// This is your real test secret API key.
const stripe = require("stripe")("sk_test_51IyLNeBPvY3ZNIaHrvP1rvWy6V2p3OlhOSHMgbua3tnyYK5zF0y5yzgKCFkPFeiFx4iQ0IcHiL3BBttdjfyIZQjW00fNi49UnZ");
app.use(express.static("."));
app.use(express.json());
const calculateOrderAmount = items => {
    // Replace this constant with a calculation of the order's amount
    // Calculate the order total on the server to prevent
    // people from directly manipulating the amount on the client
    console.log(items[0].amount)
    console.log(items);
    console.log(items[0]);
    return items[0].amount;
};


app.post("/create-payment-intent", async(req, res) => {
    const { items } = req.body;
    // Create a PaymentIntent with the order amount and currency
    const paymentIntent = await stripe.paymentIntents.create({
        amount: calculateOrderAmount(items),
        currency: "usd"
    });
    res.send({
        clientSecret: paymentIntent.client_secret
    });
});


app.get('/greet', (req, res) => {
    res.send("It is working fine")
})


app.listen(4242, () => console.log('Node server listening on port 4242!'));