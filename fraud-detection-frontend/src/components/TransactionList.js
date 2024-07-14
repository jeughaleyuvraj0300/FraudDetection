import React from 'react';

function TransactionList({ transactions }) {
  return (
    <div>
      <h2>Transactions</h2>
      {transactions.length > 0 ? (
        <ul>
          {transactions.map((transaction) => (
            <li key={transaction.id}>
              User ID: {transaction.userId}, Transaction ID: {transaction.id}, Amount: {transaction.amount}, City: {transaction.city}, Time: {transaction.time}
            </li>
          ))}
        </ul>
      ) : (
        <p>No transactions</p>
      )}
    </div>
  );
}

export default TransactionList;
