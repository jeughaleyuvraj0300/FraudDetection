import React, { useState } from 'react';
import axios from 'axios';
import "bootstrap/dist/css/bootstrap.min.css";
import './TransactionForm.css'


function TransactionForm({ setAlerts, fetchTransactions }) {
  const [userId, setUserId] = useState('');
  const [amount, setAmount] = useState('');
  const [city, setCity] = useState('');
  const [time, setTime] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    const transaction = {
      userId: parseInt(userId),
      amount: parseFloat(amount),
      city,
      time: time ? time : new Date().toISOString(), // If time is not provided, use current time
    };

    try {
      const response = await axios.post('http://localhost:8080/api/transactions', transaction);
      setAlerts(response.data);
      fetchTransactions();
    } catch (error) {
      console.error('Error adding transaction:', error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label>User ID:</label>&nbsp;&nbsp;&nbsp;&nbsp;
        <input
          type="number"
          value={userId}
          onChange={(e) => setUserId(e.target.value)}
          required
        />
      </div>
      <br></br>
      <div>
        <label>Amount:</label>&nbsp;&nbsp;&nbsp;
        <input
          type="number"
          value={amount}
          onChange={(e) => setAmount(e.target.value)}
          required
        />
      </div>
      <br></br>
      <div>
        <label>City:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input
          type="text"
          value={city}
          onChange={(e) => setCity(e.target.value)}
          required
        />
      </div>
      <br></br>
      <div>
        <label>Time:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input
          type="datetime-local"
          value={time}
          onChange={(e) => setTime(e.target.value)}
        />
      </div>
      <br></br>
      <button className='btn btn-success' type="submit">Add Transaction</button>
    </form>
  );
}

export default TransactionForm;
