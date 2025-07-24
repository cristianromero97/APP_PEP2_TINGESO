import React, { useEffect, useState } from "react";
import { useParams, useNavigate, Link } from "react-router-dom";
import {
  Box, TextField, Button, FormControl, Typography, InputLabel,
  Select, MenuItem, Chip
} from "@mui/material";
import SaveIcon from "@mui/icons-material/Save";
import paymentService from "../services/payment.service";
import discountService from "../services/discount.service";
import reservationService from "../services/reservation.service";

const AddEditPayment = () => {
  const { id } = useParams();
  const navigate = useNavigate(null);

  const [title, setTitle] = useState("Nuevo Pago");
  const [totalAmount, setTotalAmount] = useState(0);
  const [discountApplied, setDiscountApplied] = useState(0);
  const [iva, setIva] = useState(0);
  const [finalAmount, setFinalAmount] = useState(0);
  const [state, setState] = useState("");
  const [discountId, setDiscountId] = useState("");
  const [reservationId, setReservationId] = useState("");

  const [discounts, setDiscounts] = useState([]);
  const [reservations, setReservations] = useState([]);

  useEffect(() => {
    discountService.getAll().then((res) => setDiscounts(res.data));
    reservationService.getAll().then((res) => setReservations(res.data));

    if (id) {
      console.log("ID recibido:", id);
      setTitle("Editar Pago");
      paymentService.get(id).then((res) => {
        const p = res.data;
        setTotalAmount(p.totalAmount);
        setDiscountApplied(p.discountApplied);
        setIva(p.iva);
        setFinalAmount(p.finalAmount);
        setState(p.state);
        setDiscountId(p.discount?.id || "");
        setReservationId(p.reservation?.id || "");
      });
    }
  }, [id]);

  const handleSubmit = (e) => {
    e.preventDefault();

    const data = {
      totalAmount,
      discountApplied,
      iva,
      finalAmount,
      state,
      discount: { id: discountId },
      reservation: { id: reservationId },
    };

    const request = id
      ? paymentService.update(id, data)
      : paymentService.create(data);

    request
      .then(() => {
        alert(id ? "Pago actualizado" : "Pago creado");
        navigate("/payments");
      })
      .catch((err) => {
        console.error("Error al guardar el pago:", err);
      });
  };

  return (
    <Box
      component="form"
      onSubmit={handleSubmit}
      display="flex"
      flexDirection="column"
      alignItems="center"
      sx={{ width: "100%", maxWidth: 600, margin: "0 auto", padding: 3 }}
    >
      <Typography variant="h5">{title}</Typography>
      <hr style={{ width: "100%", margin: "20px 0" }} />

      <FormControl fullWidth margin="normal">
        <TextField
          label="Monto Total"
          type="number"
          value={totalAmount}
          onChange={(e) => setTotalAmount(parseFloat(e.target.value))}
          required
        />
      </FormControl>

      <FormControl fullWidth margin="normal">
        <TextField
          label="Descuento Aplicado"
          type="number"
          value={discountApplied}
          onChange={(e) => setDiscountApplied(parseFloat(e.target.value))}
          required
        />
      </FormControl>

      <FormControl fullWidth margin="normal">
        <TextField
          label="IVA"
          type="number"
          value={iva}
          onChange={(e) => setIva(parseFloat(e.target.value))}
          required
        />
      </FormControl>

      <FormControl fullWidth margin="normal">
        <TextField
          label="Monto Final"
          type="number"
          value={finalAmount}
          onChange={(e) => setFinalAmount(parseFloat(e.target.value))}
          required
        />
      </FormControl>

      <FormControl fullWidth margin="normal">
        <TextField
          label="Estado"
          value={state}
          onChange={(e) => setState(e.target.value)}
          required
        />
      </FormControl>

      <FormControl fullWidth margin="normal">
        <InputLabel id="discount-label">Descuento</InputLabel>
        <Select
          labelId="discount-label"
          value={discountId}
          onChange={(e) => setDiscountId(e.target.value)}
          required
        >
          {discounts.map((d) => (
            <MenuItem key={d.id} value={d.id}>
              {d.name} - {d.percentage}% de descuento
            </MenuItem>
          ))}
        </Select>
      </FormControl>

      <FormControl fullWidth margin="normal">
        <InputLabel id="reservation-label">Reservación</InputLabel>
        <Select
          labelId="reservation-label"
          value={reservationId}
          onChange={(e) => setReservationId(e.target.value)}
          required
        >
          {reservations.map((r) => (
            <MenuItem key={r.id} value={r.id}>
              Reservación #{r.id} - {r.date}
            </MenuItem>
          ))}
        </Select>
      </FormControl>

      <FormControl margin="normal">
        <Button
          variant="contained"
          color="primary"
          type="submit"
          startIcon={<SaveIcon />}
        >
          Guardar
        </Button>
      </FormControl>

      <hr style={{ width: "100%", margin: "20px 0" }} />

      <Link to="/payments" style={{ textDecoration: "none" }}>
        Volver a la Lista
      </Link>
    </Box>
  );
};

export default AddEditPayment;
