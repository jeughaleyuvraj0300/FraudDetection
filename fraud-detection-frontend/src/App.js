import React, { useState, useEffect } from 'react';
import axios from 'axios';
import TransactionForm from './components/TransactionForm';
import TransactionList from './components/TransactionList';
import FraudAlert from './components/FraudAlert';
import "bootstrap/dist/css/bootstrap.min.css";
import './App.css'

function App() {
  const [alerts, setAlerts] = useState([]);
  const [transactions, setTransactions] = useState([]);

  useEffect(() => {
    fetchTransactions();
  }, []);

  const fetchTransactions = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/transactions');
      setTransactions(response.data);
    } catch (error) {
      console.error('Error fetching transactions:', error);
    }
  };

  return (
    <div className="App">
      <h1>Fraud Detection System</h1>
      <br></br>
      <div className='container'>
        <div className='row'>
      <div class="container col col-md-3 border border-4 border-dark bg-info rounded"><TransactionForm setAlerts={setAlerts} fetchTransactions={fetchTransactions} /></div>
      <div class="container col col-md-3 border border-4 border-dark bg-success rounded"><TransactionList transactions={transactions} /></div>
      <div class="container col col-md-3 border border-4 border-dark bg-danger rounded"><FraudAlert alerts={alerts} /></div>
      </div>
      </div>
    </div>
  );
}

export default App;
