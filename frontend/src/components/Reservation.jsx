import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import reservationService from "../services/reservation.service";
import paymentService from "../services/payment.service";
import clientService from "../services/client.service";
import rateService from "../services/rate.service";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Button,
} from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import EventAvailableIcon from '@mui/icons-material/EventAvailable';
import PaymentIcon from '@mui/icons-material/Payment';
import CalendarMonthIcon from '@mui/icons-material/CalendarMonth';

const Reservation = () => {
  const [reservations, setReservations] = useState([]);
  const navigate = useNavigate();

  // Change applied for microservice architecture
  /*useEffect(() => {
    reservationService.getAll().then((res) => {
      setReservations(res.data);
    });
  }, []);*/

  // New change
  useEffect(() => {
  reservationService.getAll().then(async (res) => {
    const reservations = await Promise.all(
      res.data.map(async (r) => {
        const client = await clientService.get(r.clientId).then(res => res.data).catch(() => null);
        const rate = await rateService.get(r.rateId).then(res => res.data).catch(() => null);
        return { ...r, client, rate };
      })
    );
    setReservations(reservations);
    });
   }, []);

  const handleDelete = (id) => {
    if (window.confirm("¿Eliminar esta reservación?")) {
      reservationService.remove(id).then(() => {
        setReservations(reservations.filter((r) => r.id !== id));
      });
    }
  };
  // Change applied for microservice architecture 
  /*
  const handleGeneratePayment = (reservationId) => {
    const paymentData = {
      totalAmount: 0,
      discountApplied: 0,
      iva: 0,
      finalAmount: 0,
      state: "Pendiente",
      reservation: { id: reservationId },
    };

    paymentService.create(paymentData)
      .then(() => navigate("/payments"))
      .catch((err) => {
        console.error("Error al generar el pago:", err);
      });
  };*/

  const handleGeneratePayment = (reservationId, rateId) => {
  const paymentData = {
    reservationId,
    rateId,
    totalAmount: 0,
    discountApplied: 0,
    iva: 0,
    finalAmount: 0,
    state: "PENDIENTE"
  };

  paymentService.create(paymentData)
    .then(() => navigate("/payments"))
    .catch((err) => {
      console.error("Error al generar el pago:", err);
    });
  };

  return (
    <TableContainer component={Paper}>
      <div style={{ padding: "20px", height: "100vh", boxSizing: "border-box" }}>
        <Button
          variant="contained"
          color="primary"
          startIcon={<EventAvailableIcon />}
          onClick={() => navigate("/reservations/add")}
        >
          Añadir Reservación
        </Button>
        <br /><br />
        <Table sx={{ minWidth: 650 }} size="small" aria-label="tabla de reservaciones">
          <TableHead>
            <TableRow>
              <TableCell sx={{ fontWeight: "bold" }}>#</TableCell>
              <TableCell sx={{ fontWeight: "bold" }}>Fecha</TableCell>
              <TableCell sx={{ fontWeight: "bold" }}>Cantidad Personas</TableCell>
              <TableCell sx={{ fontWeight: "bold" }}>Cliente</TableCell>
              <TableCell sx={{ fontWeight: "bold" }}>Tarifa</TableCell>
              <TableCell sx={{ fontWeight: "bold" }}>Acciones</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {reservations.map((res, index) => (
              <TableRow key={res.id}>
                <TableCell>{index + 1}</TableCell>
                <TableCell>{new Date(res.date).toLocaleDateString()}</TableCell>
                <TableCell>{res.amountPeople}</TableCell>
                <TableCell>{res.client?.name || "N/A"}</TableCell>
                <TableCell>{res.rate?.price ? `₡${res.rate.price}` : "N/A"}</TableCell>
                <TableCell>
                  <Button
                    variant="contained"
                    color="info"
                    size="small"
                    onClick={() => navigate(`/reservations/edit/${res.id}`)}
                    startIcon={<EditIcon />}
                    sx={{ mr: 1, mb: 0.5 }}
                  >
                    Editar
                  </Button>
                  <Button
                    variant="contained"
                    color="error"
                    size="small"
                    onClick={() => handleDelete(res.id)}
                    startIcon={<DeleteIcon />}
                    sx={{ mr: 1, mb: 0.5 }}
                  >
                    Eliminar
                  </Button>
                  <Button
                    variant="contained"
                    color="success"
                    size="small"
                    onClick={() => handleGeneratePayment(res.id, res.rate?.id)}
                    startIcon={<PaymentIcon />}
                    sx={{ mr: 1, mb: 0.5 }}
                  >
                    Generar Pago
                  </Button>
                  <Button
                    variant="contained"
                    color="secondary"
                    size="small"
                    onClick={() => navigate(`/weekly-rack/add?reservationId=${res.id}`)}
                    startIcon={<CalendarMonthIcon />}
                    sx={{ mb: 0.5 }}
                  >
                    Reservar Rack
                  </Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </div>
    </TableContainer>
  );
};

export default Reservation;
