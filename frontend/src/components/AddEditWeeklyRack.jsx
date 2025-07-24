import React, { useEffect, useState } from "react";
import { useParams, useNavigate, Link } from "react-router-dom";
import weeklyRackService from "../services/weeklyRack.service";
import {
  Box, TextField, Button, FormControl, Select, MenuItem, InputLabel
} from "@mui/material";
import SaveIcon from "@mui/icons-material/Save";

const AddEditWeeklyRack = () => {
  const [day, setDay] = useState("");
  const [timeBlock, setTimeBlock] = useState("");
  const [reservationId, setReservationId] = useState("");
  const { id } = useParams();
  const navigate = useNavigate();
  const [title, setTitle] = useState("Nuevo Rack");

  const daysOfWeek = [
    "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"
  ];

  const timeBlocks = [
    "11:00 - 12:00", "12:00 - 13:00", "13:00 - 14:00", "14:00 - 15:00",
    "15:00 - 16:00", "16:00 - 17:00", "17:00 - 18:00", "18:00 - 19:00", "19:00 - 20:00"
  ];

  useEffect(() => {
    if (id) {
      setTitle("Editar Rack");
      weeklyRackService
        .get(id)
        .then((res) => {
          const rack = res.data;
          setDay(rack.day);
          setTimeBlock(rack.timeBlock);
          setReservationId(rack.reservation ? rack.reservation.id : "");
        })
        .catch((error) => console.error("Error al cargar el rack:", error));
    }
  }, [id]);

  const saveRack = (e) => {
    e.preventDefault();
    const data = {
      day,
      timeBlock,
      reservationId: reservationId ? parseInt(reservationId) : null,
    };

    const req = id
      ? weeklyRackService.update(id, data)
      : weeklyRackService.create(data);

    req
      .then(() => {
        alert("Rack guardado correctamente");
        navigate("/weekly-rack");
      })
      .catch((error) => console.error("Error al guardar:", error));
  };

  return (
    <Box display="flex" flexDirection="column" alignItems="center">
      <h3>{title}</h3>
      <form onSubmit={saveRack}>
        <FormControl fullWidth margin="normal">
          <InputLabel id="day-label">Día</InputLabel>
          <Select
            labelId="day-label"
            value={day}
            label="Día"
            onChange={(e) => setDay(e.target.value)}
            required
          >
            {daysOfWeek.map((d) => (
              <MenuItem key={d} value={d}>
                {d}
              </MenuItem>
            ))}
          </Select>
        </FormControl>

        <FormControl fullWidth margin="normal">
          <InputLabel id="timeblock-label">Bloque de Tiempo</InputLabel>
          <Select
            labelId="timeblock-label"
            value={timeBlock}
            label="Bloque de Tiempo"
            onChange={(e) => setTimeBlock(e.target.value)}
            required
          >
            {timeBlocks.map((block) => (
              <MenuItem key={block} value={block}>
                {block}
              </MenuItem>
            ))}
          </Select>
        </FormControl>

        <FormControl fullWidth margin="normal">
          <TextField
            label="ID Reservación"
            type="number"
            value={reservationId}
            onChange={(e) => setReservationId(e.target.value)}
          />
        </FormControl>

        <Button
          variant="contained"
          color="primary"
          type="submit"
          startIcon={<SaveIcon />}
        >
          Guardar
        </Button>
      </form>
      <br />
      <Link to="/weekly-rack">Volver a la Lista</Link>
    </Box>
  );
};

export default AddEditWeeklyRack;
