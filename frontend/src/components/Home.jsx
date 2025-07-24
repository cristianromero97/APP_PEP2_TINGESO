const Home = () => {
  return (
    <div
      style={{
        height: "100vh",
        display: "flex",
        flexDirection: "column",
        backgroundColor: "#FFF9C4", // Yellow background  
        color: "black", // Color text
        padding: "2rem",
      }}
    >
      <div style={{ flex: 1 }}>
        <h1>Gokart: Sistema de Reservación de Karting</h1>
        <p>
          Gokart es una aplicación web para gestionar la reserva de karts
          para un circuito de carreras. Cuenta con la administración de expertos
          para la gestión del negocio y garantizar una mejor experiencia.
          
          Esta aplicación ha sido desarrollada usando tecnologías como{" "}
          <a href="https://spring.io/projects/spring-boot" style={{ color: "inherit" }}>
            Spring Boot
          </a> (para el backend), <a href="https://reactjs.org/" style={{ color: "inherit" }}>
            React
          </a> (para el frontend) y{" "}
          <a href="https://www.mysql.com/products/community/" style={{ color: "inherit" }}>
            MySQL
          </a> (para la base de datos).
        </p>
      </div>

      <div
        style={{
          width: "100%", 
            objectFit: "cover",
            height: "100vh",
            display: "flex",
            flexDirection: "column",
            backgroundImage: "url('src/assets/Kart.jpg')", 
            backgroundSize: "cover",
            backgroundPosition: "center",
        }}
      />
    </div>
  );
};

export default Home;
