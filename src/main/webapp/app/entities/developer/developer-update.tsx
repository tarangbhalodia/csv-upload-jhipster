import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './developer.reducer';
import { IDeveloper } from 'app/shared/model/developer.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDeveloperUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IDeveloperUpdateState {
  isNew: boolean;
}

export class DeveloperUpdate extends React.Component<IDeveloperUpdateProps, IDeveloperUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { developerEntity } = this.props;
      const entity = {
        ...developerEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/developer');
  };

  render() {
    const { developerEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="csvuploadApp.developer.home.createOrEditLabel">Create or edit a Developer</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : developerEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="developer-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="firstNameLabel" for="firstName">
                    First Name
                  </Label>
                  <AvField
                    id="developer-firstName"
                    type="text"
                    name="firstName"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      minLength: { value: 1, errorMessage: 'This field is required to be at least 1 characters.' },
                      pattern: { value: '[a-zA-Z]{2,}', errorMessage: "This field should follow pattern for '[a-zA-Z]{2,}'." }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="lastNameLabel" for="lastName">
                    Last Name
                  </Label>
                  <AvField
                    id="developer-lastName"
                    type="text"
                    name="lastName"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      pattern: { value: '[a-zA-Z]{2,}', errorMessage: "This field should follow pattern for '[a-zA-Z]{2,}'." }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="topSkillLabel" for="topSkill">
                    Top Skill
                  </Label>
                  <AvField id="developer-topSkill" type="text" name="topSkill" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/developer" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  developerEntity: storeState.developer.entity,
  loading: storeState.developer.loading,
  updating: storeState.developer.updating,
  updateSuccess: storeState.developer.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DeveloperUpdate);
