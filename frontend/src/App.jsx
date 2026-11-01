import { Routes, Route, Navigate } from 'react-router-dom'
import { useAuth } from './context/AuthContext.jsx'
import Sidebar from './components/Sidebar.jsx'
import ProtectedRoute from './components/ProtectedRoute.jsx'
import Login from './pages/Login.jsx'
import Dashboard from './pages/Dashboard.jsx'
import Usuarios from './pages/Usuarios.jsx'
import Clientes from './pages/Clientes.jsx'
import Recepciones from './pages/Recepciones.jsx'
import Ordenes from './pages/Ordenes.jsx'
import Comprobantes from './pages/Comprobantes.jsx'
import Logs from './pages/Logs.jsx'

export default function App() {
  const { isAuthenticated } = useAuth()

  if (!isAuthenticated) {
    return (
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="*" element={<Navigate to="/login" replace />} />
      </Routes>
    )
  }

  return (
    <div className="d-flex">
      <Sidebar />
      <div className="flex-grow-1" style={{ height: '100vh', overflow: 'auto' }}>
        <Routes>
          <Route path="/" element={<Dashboard />} />
          <Route path="/usuarios" element={<ProtectedRoute roles={['ADMINISTRADOR']}><Usuarios /></ProtectedRoute>} />
          <Route path="/clientes" element={<ProtectedRoute roles={['ADMINISTRADOR','RECEPCIONISTA']}><Clientes /></ProtectedRoute>} />
          <Route path="/recepciones" element={<ProtectedRoute roles={['ADMINISTRADOR','RECEPCIONISTA']}><Recepciones /></ProtectedRoute>} />
          <Route path="/ordenes" element={<ProtectedRoute roles={['ADMINISTRADOR','RECEPCIONISTA']}><Ordenes /></ProtectedRoute>} />
          <Route path="/comprobantes" element={<ProtectedRoute roles={['ADMINISTRADOR','RECEPCIONISTA']}><Comprobantes /></ProtectedRoute>} />
          <Route path="/logs" element={<ProtectedRoute roles={['ADMINISTRADOR']}><Logs /></ProtectedRoute>} />
          <Route path="/login" element={<Navigate to="/" replace />} />
          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </div>
    </div>
  )
}
