import { useState } from "react";
import api from "../services/api";

const VEHICLES=[{id:"VEH002",label:"ND-5678"}];
const SHOPS=[{id:"SHOP001",name:"City Shop"}];
const PRODUCTS=[{id:"PRO001",name:"Milk"}];

export default function ShopSupplyPage(){
  const [vehicleId,setVehicleId] = useState(VEHICLES[0].id);
  const [rows,setRows] = useState([{shopId:SHOPS[0].id, proId:PRODUCTS[0].id, qtySupplied:0, unitPrice:0}]);

  async function handleSubmit(e){
    e.preventDefault();
    const payload={
      supplyId:"SS"+Date.now(),
      vehicleId,
      salesmanUserId:"USR002",
      supplyDate:new Date().toISOString().slice(0,10),
      items:rows
    };
    await api.post("/shop-supplies", payload);
    alert("Supply saved");
  }

  return (
    <form onSubmit={handleSubmit}>
      <h2>Deliver to Shops</h2>
      {rows.map((r,i)=>(
        <div key={i}>
          <select value={r.shopId} onChange={e=>{
            const copy=[...rows]; copy[i].shopId=e.target.value; setRows(copy);
          }}>
            {SHOPS.map(s=><option key={s.id} value={s.id}>{s.name}</option>)}
          </select>
          <select value={r.proId} onChange={e=>{
            const copy=[...rows]; copy[i].proId=e.target.value; setRows(copy);
          }}>
            {PRODUCTS.map(p=><option key={p.id} value={p.id}>{p.name}</option>)}
          </select>
          <input type="number" placeholder="Qty" value={r.qtySupplied} onChange={e=>{
            const copy=[...rows]; copy[i].qtySupplied=parseInt(e.target.value); setRows(copy);
          }}/>
          <input type="number" placeholder="Price" value={r.unitPrice} onChange={e=>{
            const copy=[...rows]; copy[i].unitPrice=parseFloat(e.target.value); setRows(copy);
          }}/>
        </div>
      ))}
      <button type="submit">Save</button>
    </form>
  );
}
