import React from "react";

import { connect } from "react-redux";

export default function(ComposedComponent) {
    class Authenticate extends React.Component {
        componentDidMount() {
            this._checkAndRedirect();
        }

        componentDidUpdate() {
            this._checkAndRedirect();
        }

        _checkAndRedirect() {
            const { isAuthenticated } = this.props;

            if (!isAuthenticated) {
                document.location = "/";
            }
        }

        render() {
            return (
                <div>
                    {this.props.isAuthenticated ? (
                        <ComposedComponent {...this.props} />
                    ) : null}
                </div>
            );
        }
    }

    const mapStateToProps = state => {
        return {
            isAuthenticated: state.auth.get("isAuthenticated")
        };
    };
    return connect(
        mapStateToProps,
        {}
    )(Authenticate);
}
