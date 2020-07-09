import React from "react";
import {AppBar, IconButton, Toolbar, Typography} from "@material-ui/core";

export default class Header extends React.Component {
    render() {
        return (
            <div >
                <AppBar position="static">
                    <Toolbar variant="dense">
                        <h1>Instrument distrubution tree viewer</h1>
                        <IconButton edge="start" color="inherit" aria-label="menu">
                        </IconButton>
                        <Typography variant="h6" color="inherit">
                        </Typography>
                    </Toolbar>
                </AppBar>
            </div>
        );
    }
}
