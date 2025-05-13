import { useAppDispatch, useAppSelector } from 'app/config/store';
import React, { useEffect, useRef, useState } from 'react';

import { Link, RouteComponentProps } from 'react-router-dom';
import { TextFormat, Translate, ValidatedBlobField, ValidatedField, ValidatedForm, openFile, translate } from 'react-jhipster';

import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Alert, Button, Row } from 'reactstrap';
import { createEntity, getEntities, reset, updateEntity, getComponentesbyEcosistema, deleteEntity } from './ecosistema-componente.reducer';
import { ScrollPanel } from 'primereact/scrollpanel';
import { Dialog } from 'primereact/dialog';
import { getEntities as getComponentes } from 'app/entities/componentes/componentes.reducer';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Toast } from 'primereact/toast';

const ExpandableTableComponente = props => {
  const dispatch = useAppDispatch();
  const [forma, setLayout] = useState(null);
  const [componenteDialogNew, setComponenteDialogNew] = useState(false);
  const [isNew, setNew] = useState(false);

  const componenteList = useAppSelector(state => state.ecosistemaComponente.entities);
  const loading = useAppSelector(state => state.ecosistemaComponente.loading);
  const componentes = useAppSelector(state => state.componentes.entities);

  const [customers, setCustomers] = useState([]);
  const [expandedRows, setExpandedRows] = useState([]);

  const emptyComponente = {
    id: null,
    link: null,
    documentoUrlContentType: null,
    descripcion: null,
    componentehijo: null,
    documentoUrl: '',
    componente: null,
    ecosistema: null,
  };
  const [selectedComponente, setSelectedComponente] = useState(emptyComponente);
  const [componente, setComponente] = useState([]);

  useEffect(() => {
    dispatch(getComponentesbyEcosistema(props.id));
    dispatch(getComponentes({}));
    setLayout('list');
  }, []);

  useEffect(() => {
    if (!loading) setComponente(componenteList);
  }, [loading]);

  const headerTemplate = data => {
    return (
      <React.Fragment>
        <span className=" fw-bold">
          {data.componente.componente} ({calculateCustomerTotal(data.componente.componente)})
        </span>
      </React.Fragment>
    );
  };
  const calculateCustomerTotal = name => {
    let total = 0;

    if (componente) {
      total = componente.filter(arrayElement => arrayElement.componente.componente === name).length;
    }

    return total;
  };

  const footerTemplate = data => {
    return (
      <div>
        <td colSpan={4} style={{ textAlign: 'right' }}>
          Total
        </td>
        <td>{calculateCustomerTotal(data.componente.componente)}</td>
      </div>
    );
  };

  const setExpandedRows1 = data => {
    setExpandedRows(data);
  };
  const hideDialogNuevo = () => {
    setComponenteDialogNew(false);
  };
  const verReto = componente1 => {
    setComponenteDialogNew(true);
    setSelectedComponente(componente1);
  };
  const actionBodyTemplate = rowData => {
    return (
      <div className=" flex align-items-end justify-content-end">
        <Button onClick={() => verReto(rowData)}>
          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline"></span>
        </Button>
      </div>
    );
  };

  return (
    <div className="col-12">
      <ScrollPanel style={{ width: '100%', height: '300px' }}>
        <DataTable
          value={componenteList}
          rowGroupMode="subheader"
          groupRowsBy="componente.componente"
          sortMode="single"
          sortField="componente.componente"
          sortOrder={1}
          responsiveLayout="scroll"
          expandableRowGroups
          expandedRows={expandedRows}
          emptyMessage="No hay Componentes"
          onRowToggle={e => setExpandedRows1(e.data)}
          rowGroupHeaderTemplate={headerTemplate}
        >
          <Column field="componentehijo" header="Componente"></Column>
          <Column field="descripcion" header="Descripción"></Column>

          <Column body={actionBodyTemplate} headerStyle={{ minWidth: '10rem' }}></Column>
        </DataTable>
      </ScrollPanel>

      <Dialog
        visible={componenteDialogNew}
        style={{ width: '500px' }}
        header="Componente"
        modal
        className="p-fluid  "
        onHide={hideDialogNuevo}
      >
        <Row className="justify-content-center">
          {loading ? (
            <p>Cargando...</p>
          ) : (
            <div className="col-12 border-round-xl  mb-2">
              <div className="flex flex-column xl:flex-row xl:align-items-center p-2 gap-4">
                <div className="flex flex-column sm:flex-row justify-content-between align-items-center xl:align-items-start flex-1 gap-4">
                  <div className="flex flex-column align-content-end align-items-center sm:align-items-start gap-3">
                    <div className="text-base font-bold ">Tipo de Componente: {selectedComponente?.componente?.componente} </div>
                    <div className="text-base font-bold ">Componente: {selectedComponente?.componentehijo} </div>

                    <div className="surface-overlay w-full  mb-2  overflow-hidden text-overflow-ellipsis">
                      {selectedComponente?.descripcion}
                    </div>

                    <div className="text-base font-bold ">
                      Documento:
                      {selectedComponente.documentoUrlContentType ? (
                        <a
                          className="text-primary"
                          onClick={openFile(selectedComponente.documentoUrlContentType, selectedComponente.documentoUrl)}
                        >
                          &nbsp; &nbsp;
                          <Translate contentKey="entity.action.open"> Open</Translate>
                          &nbsp;
                        </a>
                      ) : null}
                    </div>

                    {selectedComponente.link ? (
                      <div className="text-base font-bold ">
                        Link: &nbsp; &nbsp;
                        <a href={selectedComponente.link} target="_blank" rel="noopener noreferrer">
                          {selectedComponente.link}
                        </a>
                      </div>
                    ) : null}
                  </div>
                </div>
              </div>
            </div>
          )}
        </Row>
      </Dialog>
    </div>
  );
};

export default ExpandableTableComponente;
