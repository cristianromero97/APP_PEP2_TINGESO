import React, { useEffect, useState } from "react";
import { useParams, useNavigate, Link } from "react-router-dom";
import {
  Box, TextField, Button, FormControl, Typography, InputLabel,
  Select, MenuItem, Chip
} from "@mui/material";
import SaveIcon from "@mui/icons-material/Save";
import reservationService from "../services/reservation.service";
import clientService from "../services/client.service";
import rateService from "../services/rate.service";
import kartService from "../services/kart.service";

const AddEditReservation = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const [title, setTitle] = useState("Nueva Reservación");
  const [date, setDate] = useState("");
  const [amountPeople, setAmountPeople] = useState(1);
  const [clientId, setClientId] = useState("");
  const [rateId, setRateId] = useState("");
  const [kartIds, setKartIds] = useState([]);
  const [guestIds, setGuestIds] = useState([]); 

  const [clients, setClients] = useState([]);
  const [rates, setRates] = useState([]);
  const [karts, setKarts] = useState([]);

  useEffect(() => {
    clientService.getAll().then((res) => setClients(res.data));
    rateService.getAll().then((res) => setRates(res.data));
    kartService.getAll().then((res) => setKarts(res.data));

    if (id) {
      setTitle("Editar Reservación");
      reservationService.get(id).then((res) => {
        const r = res.data;
        setDate(r.date.split("T")[0]);
        setAmountPeople(r.amountPeople);
        setClientId(r.client?.id || "");
        setRateId(r.rate?.id || "");
        setKartIds(r.karts.map(k => k.id));
        setGuestIds(r.guests?.map(g => g.id) || []);
      });
    }
  }, [id]);
  /*
  const handleSubmit = (e) => {
    e.preventDefault();

    const expectedGuests = amountPeople > 1 ? amountPeople - 1 : 0;
    if (guestIds.length !== expectedGuests) {
      alert(`Debes seleccionar exactamente ${expectedGuests} invitado(s).`);
      return;
    }

    const data = {
      date,
      amountPeople,
      client: { id: clientId },
      rate: { id: rateId },
      karts: kartIds.map((id) => ({ id })),
      guests: guestIds.map((id) => ({ id })),
    };

    const request = id
      ? reservationService.update(id, data)
      : reservationService.create(data);

    request
      .then(() => {
        alert(id ? "Reservación actualizada" : "Reservación creada");
        navigate("/reservations");
      })
      .catch((err) => {
        console.error("Error al guardar la reservación:", err);
      });
  };*/

  const handleSubmit = (e) => {
  e.preventDefault();

  if (!clientId) {
    alert("Debe seleccionar un cliente.");
    return;
  }
  /*
  const data = {
    date,
    amountPeople,
    client: { id: clientId },
    rate: { id: rateId },
    karts: kartIds.map((id) => ({ id })),
    guests: guestIds.map((id) => ({ id })),
  };*/
  const data = {
  date,
  amountPeople,
  clientId,
  rateId,
  kartIds,
  guestIds,
  };

  const request = id
    ? reservationService.update(id, data)
    : reservationService.create(data);

  request
    .then(() => {
      alert(id ? "Reservación actualizada" : "Reservación creada");
      navigate("/reservations");
    })
    .catch((err) => {
      console.error("Error al guardar la reservación:", err);
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
          label="Fecha"
          type="date"
          InputLabelProps={{ shrink: true }}
          value={date}
          onChange={(e) => setDate(e.target.value)}
          required
        />
      </FormControl>

      <FormControl fullWidth margin="normal">
        <TextField
          label="Cantidad de Personas"
          type="number"
          value={amountPeople}
          onChange={(e) => setAmountPeople(parseInt(e.target.value))}
          required
        />
      </FormControl>

      <FormControl fullWidth margin="normal">
        <InputLabel id="client-label">Cliente</InputLabel>
        <Select
          labelId="client-label"
          value={clientId}
          onChange={(e) => setClientId(e.target.value)}
          required
        >
          {clients.map((c) => (
            <MenuItem key={c.id} value={c.id}>
              {c.name}
            </MenuItem>
          ))}
        </Select>
      </FormControl>

      {/* NEW FIELD: Guests */}
      {amountPeople > 1 && (
        <FormControl fullWidth margin="normal">
          <InputLabel id="guests-label">Invitados</InputLabel>
          <Select
            labelId="guests-label"
            multiple
            value={guestIds}
            onChange={(e) => setGuestIds(e.target.value)}
            renderValue={(selected) => (
              <Box sx={{ display: "flex", flexWrap: "wrap", gap: 0.5 }}>
                {selected.map((id) => {
                  const guest = clients.find((c) => c.id === id);
                  return <Chip key={id} label={guest?.name || id} />;
                })}
              </Box>
            )}
          >
            {clients
              .filter((c) => c.id !== clientId)
              .map((c) => (
                <MenuItem key={c.id} value={c.id}>
                  {c.name}
                </MenuItem>
              ))}
          </Select>
        </FormControl>
      )}

      <FormControl fullWidth margin="normal">
        <InputLabel id="rate-label">Tarifa</InputLabel>
        <Select
          labelId="rate-label"
          value={rateId}
          onChange={(e) => setRateId(e.target.value)}
          required
        >
          {rates.map((r) => (
            <MenuItem key={r.id} value={r.id}>
              {/* Updated to show the rate price */}
              {r.price? `₡${r.price} - ${r.laps} Vueltas - Tiempo máximo por vuelta: ${r.maximumTimeLaps} mins - Tiempo máximo de reservación: ${r.maximumTimeReservation} mins` : "Tarifa no definida"}
            </MenuItem>
          ))}
        </Select>
      </FormControl>

      <FormControl fullWidth margin="normal">
        <InputLabel id="karts-label">Karts</InputLabel>
        <Select
          labelId="karts-label"
          multiple
          value={kartIds}
          onChange={(e) => setKartIds(e.target.value)}
        >
          {karts.map((k) => (
            <MenuItem key={k.id} value={k.id}>
              {k.model}
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

      <Link to="/reservations" style={{ textDecoration: "none" }}>
        Volver a la Lista
      </Link>
    </Box>
  );
};

export default AddEditReservation;
