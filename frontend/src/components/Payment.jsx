import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import paymentService from "../services/payment.service";
import reportService from "../services/report.service";
import {
  Table, TableBody, TableCell, TableContainer, TableHead,
  TableRow, Paper, Button
} from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import AttachMoneyIcon from "@mui/icons-material/AttachMoney";
import PaymentIcon from "@mui/icons-material/Payment";
import AssessmentIcon from "@mui/icons-material/Assessment";
import EmailIcon from "@mui/icons-material/Email";  

const Payment = () => {
  const [payments, setPayments] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    paymentService.getAll().then((res) => {
      setPayments(res.data);
    });
  }, []);

  const handleDelete = (id) => {
    if (window.confirm("¿Eliminar este pago?")) {
      paymentService.remove(id).then(() => {
        setPayments(payments.filter((p) => p.id !== id));
      });
    }
  };

  const handleEdit = (id) => {
    navigate(`/payments/edit/${id}`);
  };

  const handlePay = (paymentId) => {
    paymentService.updateState(paymentId, 'PAGADO')
      .then(response => {
        console.log("Estado actualizado", response.data);
      })
      .catch(error => {
        console.error("Error al actualizar el estado", error);
      });
  };

  const generateReport = (type) => {
    reportService.generate(type)
      .then(res => {
        alert(`Reporte de tipo "${type}" generado correctamente.`);
      })
      .catch(err => {
        console.error("Error generando reporte", err);
        alert("Hubo un error al generar el reporte.");
      });
  };

  const handleSendPdf = (paymentId) => {
    paymentService.sendPaymentPdf(paymentId)
      .then(response => {
        alert("Comprobante de pago enviado correctamente.");
      })
      .catch(error => {
        console.error("Error al enviar el correo", error);
        alert("Hubo un error al enviar el comprobante.");
      });
  };


  return (
    <TableContainer component={Paper}>
      <div style={{ padding: "20px", height: "100vh", boxSizing: "border-box" }}>
        <div style={{ display: "flex", justifyContent: "space-between", marginBottom: "20px" }}>
          <Button
            variant="contained"
            color="primary"
            startIcon={<AttachMoneyIcon />}
            onClick={() => navigate("/payments/add")}
          >
            Añadir Pago
          </Button>
          <div style={{ display: "flex", gap: "10px" }}>
            <Button
              variant="outlined"
              startIcon={<AssessmentIcon />}
              onClick={() => generateReport("time")}
              sx={{ backgroundColor: "#FFEB3B", color: "#000", "&:hover": { backgroundColor: "#FDD835" } }}
            >
              Reporte Tiempo
            </Button>
            <Button
              variant="outlined"
              startIcon={<AssessmentIcon />}
              onClick={() => generateReport("people")}
              sx={{ backgroundColor: "#FFEB3B", color: "#000", "&:hover": { backgroundColor: "#FDD835" } }}
            >
              Reporte Personas
            </Button>
            <Button
              variant="outlined"
              startIcon={<AssessmentIcon />}
              onClick={() => generateReport("laps")}
              sx={{ backgroundColor: "#FFEB3B", color: "#000", "&:hover": { backgroundColor: "#FDD835" } }}
            >
              Reporte Vueltas
            </Button>
          </div>
        </div>

        <Table sx={{ minWidth: 650 }} size="small" aria-label="tabla de pagos">
          <TableHead>
            <TableRow>
              <TableCell sx={{ fontWeight: "bold" }}>#</TableCell>
              <TableCell sx={{ fontWeight: "bold" }}>Total Final</TableCell>
              <TableCell sx={{ fontWeight: "bold" }}>Total</TableCell>
              <TableCell sx={{ fontWeight: "bold" }}>Descuento</TableCell>
              <TableCell sx={{ fontWeight: "bold" }}>IVA</TableCell>
              <TableCell sx={{ fontWeight: "bold" }}>Estado</TableCell>
              <TableCell sx={{ fontWeight: "bold" }}>Operaciones</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {payments.map((payment, index) => (
              <TableRow key={payment.id}>
                <TableCell>{index + 1}</TableCell>
                <TableCell>{payment.finalAmount}</TableCell>
                <TableCell>{payment.totalAmount}</TableCell>
                <TableCell>{payment.discountApplied}</TableCell>
                <TableCell>{payment.iva}</TableCell>
                <TableCell>{payment.state}</TableCell>
                <TableCell>
                  {payment.state === "PENDIENTE" && (
                    <Button
                      variant="contained"
                      color="success"
                      size="small"
                      onClick={() => handlePay(payment.id)}
                      startIcon={<PaymentIcon />}
                      sx={{ mr: 1 }}
                    >
                      Pagar
                    </Button>
                  )}
                  <Button
                    size="small"
                    variant="contained"
                    color="info"
                    onClick={() => handleEdit(payment.id)}
                    startIcon={<EditIcon />}
                    sx={{ mr: 1 }}
                  >
                    Editar
                  </Button>
                  <Button
                    size="small"
                    variant="contained"
                    color="error"
                    onClick={() => handleDelete(payment.id)}
                    startIcon={<DeleteIcon />}
                    sx={{ mr: 1 }}
                  >
                    Eliminar
                  </Button>
                  <Button
                    size="small"
                    variant="contained"
                    color="primary"
                    onClick={() => handleSendPdf(payment.id)} 
                    startIcon={<EmailIcon />}
                  >
                    Enviar PDF
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

export default Payment; 