import React from 'react';

function FraudAlert({ alerts }) {
  return (
    <div>
      <h2>Fraud Alerts</h2>
      {alerts.length > 0 ? (
        <ul>
          {alerts.map((alert, index) => (
            <li key={index}>
              {alert.message} for transaction ID: {alert.transaction.id}
            </li>
          ))}
        </ul>
      ) : (
        <p>No fraud alerts</p>
      )}
    </div>
  );
}

export default FraudAlert;
