import { useEffect, useState } from 'react'
import api from '../api/axios.js'

export default function Logs() {
  const [logs, setLogs] = useState([])
  useEffect(() => { api.get('/logs/comprobantes').then(r => setLogs(r.data)) }, [])

  return (
    <div className="container-fluid p-4">
      <h2>Logs de Auditoria - Impresion de Comprobantes</h2>
      <table className="table table-striped">
        <thead><tr><th>Fecha</th><th>Usuario</th><th>Codigo</th><th>Motivo</th></tr></thead>
        <tbody>
          {logs.map((l) => (
            <tr key={l.id}>
              <td>{new Date(l.fechaImpresion).toLocaleString()}</td>
              <td>{l.usuarioUsername}</td>
              <td>{l.codigoInterno}</td>
              <td>{l.motivo}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}
