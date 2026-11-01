import { useEffect, useState } from 'react'
import api from '../api/axios.js'

export default function Ordenes() {
  const [ordenes, setOrdenes] = useState([])
  const [selected, setSelected] = useState(null)
  const [editObs, setEditObs] = useState('')
  const [error, setError] = useState('')

  const cargar = async () => {
    const { data } = await api.get('/ordenes')
    setOrdenes(data)
  }
  useEffect(() => { cargar() }, [])

  const handleUpdate = async (codigo) => {
    try {
      await api.put(`/ordenes/${codigo}`, { observaciones: editObs })
      setSelected(null)
      cargar()
    } catch (err) { setError(err.response?.data?.error || 'Error') }
  }

  const handleDelete = async (codigo) => {
    if (!confirm('Eliminar orden ' + codigo + '?')) return
    await api.delete(`/ordenes/${codigo}`)
    cargar()
  }

  return (
    <div className="container-fluid p-4">
      <h2>Ordenes de Servicio</h2>
      {error && <div className="alert alert-danger">{error}</div>}
      <table className="table table-striped">
        <thead><tr><th>Codigo</th><th>Cliente</th><th>Equipo</th><th>Estado</th><th>Fecha</th><th>Acciones</th></tr></thead>
        <tbody>
          {ordenes.map((o) => (
            <tr key={o.ordenId}>
              <td>{o.codigoInterno}</td><td>{o.clienteNombre}</td><td>{o.equipoMarca} {o.equipoModelo}</td><td>{o.estado}</td><td>{new Date(o.fechaRecepcion).toLocaleString()}</td>
              <td>
                <button className="btn btn-sm btn-warning me-1" onClick={()=>{setSelected(o.codigoInterno); setEditObs(o.mensaje||'')}}>Editar</button>
                <button className="btn btn-sm btn-danger" onClick={()=>handleDelete(o.codigoInterno)}>Eliminar</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {selected && (
        <div className="modal show d-block" style={{background:'rgba(0,0,0,0.5)'}}>
          <div className="modal-dialog">
            <div className="modal-content">
              <div className="modal-header"><h5 className="modal-title">Editar {selected}</h5><button className="btn-close" onClick={()=>setSelected(null)}></button></div>
              <div className="modal-body">
                <div className="mb-2"><label className="form-label">Observaciones</label><input className="form-control" value={editObs} onChange={(e)=>setEditObs(e.target.value)} /></div>
                <button className="btn btn-primary w-100" onClick={()=>handleUpdate(selected)}>Guardar</button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}
