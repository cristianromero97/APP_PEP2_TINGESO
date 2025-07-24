import { BrowserRouter, Routes, Route } from "react-router-dom";
import Clientes from "./components/Client";
import AddEditClientes from "./components/AddEditClient";
import Home from "./components/Home";
import Layout from "./components/Layout";
import Kart from "./components/Kart"; 
import AddEditKarts from "./components/AddEditKart";
import AddEditRates from "./components/AddEditRate";
import Rates from "./components/Rate";
import AddEditDiscount from "./components/AddEditDiscount";
import Discounts from "./components/Discount";
import Reservation from "./components/Reservation";
import AddEditReservation from "./components/AddEditReservation";
import Payment from "./components/Payment";
import AddEditPayment from "./components/AddEditPayment";
import AddEditReport from "./components/AddEditReport";
import Report from "./components/Report";
import AddEditWeeklyRack from "./components/AddEditWeeklyRack";
import WeeklyRack from "./components/WeeklyRack";
import DiscountDay from "./components/DiscountDay";
import AddEditDiscountDay from "./components/AddEditDiscountDay";
import DiscountVisit from "./components/DiscountVisit";
import AddEditDiscountVisit from "./components/AddEditDiscountVisit";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route path="clients" element={<Clientes />} />
          <Route path="home" element={<Home />} />
          <Route path="clients/add" element={<AddEditClientes />} />
          <Route path="client/edit/:id" element={<AddEditClientes />} />
          <Route path="karts" element={<Kart />} />
          <Route path="karts/add" element={<AddEditKarts />} />
          <Route path="kart/edit/:id" element={<AddEditKarts />} />
          <Route path="rates" element={<Rates />} />
          <Route path="rates/add" element={<AddEditRates />} />
          <Route path="rates/edit/:id" element={<AddEditRates />} />
          <Route path="discounts" element={<Discounts />} />
          <Route path="discounts/add" element={<AddEditDiscount />} />
          <Route path="discounts/edit/:id" element={<AddEditDiscount />} />
          <Route path="reservations" element={<Reservation />} />
          <Route path="reservations/add" element={<AddEditReservation />} />
          <Route path="reservations/edit/:id" element={<AddEditReservation />} />
          <Route path="payments" element={<Payment />} />
          <Route path="payments/add" element={<AddEditPayment />} />
          <Route path="payments/:id" element={<AddEditPayment />} />
          <Route path="/payments/edit/:id" element={<AddEditPayment />} />
          <Route path="reports" element={<Report />} />
          <Route path="reports/add" element={<AddEditReport />} />
          <Route path="reports/edit/:id" element={<AddEditReport />} />
          <Route path="weekly-rack" element={<WeeklyRack />} />
          <Route path="weekly-rack/add" element={<AddEditWeeklyRack />} />
          <Route path="weekly-rack/edit/:id" element={<AddEditWeeklyRack />} />
          <Route path="discount-day" element={<DiscountDay />} />
          <Route path="discount-day/add" element={<AddEditDiscountDay />} />
          <Route path="discount-day/edit/:id" element={<AddEditDiscountDay />} />
          <Route path="discount-visit" element={<DiscountVisit />} />
          <Route path="discount-visit/add" element={<AddEditDiscountVisit />} />
          <Route path="discount-visit/edit/:id" element={<AddEditDiscountVisit />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;