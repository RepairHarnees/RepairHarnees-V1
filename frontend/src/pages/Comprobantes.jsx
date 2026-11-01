import { useEffect, useState } from 'react'
import api from '../api/axios.js'

export default function Comprobantes() {
  const [ordenes, setOrdenes] = useState([])
  const [selected, setSelected] = useState('')
  const [motivo, setMotivo] = useState('Entrega de comprobante al cliente')
  const [msg, setMsg] = useState('')

  useEffect(() => { api.get('/ordenes').then(r => setOrdenes(r.data)) }, [])

  const handlePrint = async () => {
    if (!selected) return
    try {
      const res = await api.get(`/ordenes/${selected}/comprobante`, { params: { motivo }, responseType: 'blob' })
      const url = window.URL.createObjectURL(new Blob([res.data], { type: 'application/pdf' }))
      const a = document.createElement('a')
      a.href = url
      a.download = `comprobante-${selected}.pdf`
      a.click()
      window.URL.revokeObjectURL(url)
      setMsg('Comprobante generado y log registrado.')
    } catch (err) { setMsg('Error al generar comprobante') }
  }

  return (
    <div className="container-fluid p-4">
      <h2>Comprobantes de Recepcion</h2>
      {msg && <div className="alert alert-info">{msg}</div>}
      <div className="row">
        <div className="col-md-6">
          <div className="card">
            <div className="card-body">
              <h5>Generar comprobante PDF</h5>
              <div className="mb-2">
                <label className="form-label">Orden</label>
                <select className="form-select" value={selected} onChange={(e) => setSelected(e.target.value)}>
                  <option value="">Seleccione una orden</option>
                  {ordenes.map((o) => <option key={o.ordenId} value={o.codigoInterno}>{o.codigoInterno} - {o.clienteNombre}</option>)}
                </select>
              </div>
              <div className="mb-2">
                <label className="form-label">Motivo</label>
                <input className="form-control" value={motivo} onChange={(e) => setMotivo(e.target.value)} />
              </div>
              <button className="btn btn-primary w-100" onClick={handlePrint} disabled={!selected}>Generar PDF</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}
