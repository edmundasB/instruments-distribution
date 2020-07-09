import axios from 'axios';
import React from "react";
import Tree from "react-d3-tree";
import FileUpload from "./FileUpload";
import {Button, Grid, Input} from "@material-ui/core";

const containerStyles = {
    width: "100%",
    height: "100vh"
};

export default class LedgerTree extends React.PureComponent {

    // @ts-ignore
    constructor(props) {
        super(props);

        this.updateInput = this.updateInput.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }


    updateInput(event: { target: { value: any; }; }){
        this.setState({username : event.target.value})
    }


    handleSubmit(){
        this.getTreeData();
    }

    state = {
        treeData: {},
        translate: undefined,
        username: ""
    }
    private treeContainer: HTMLDivElement | null | undefined;

    componentDidMount() {
        let dimensions: any;
        // @ts-ignore
        dimensions = this.treeContainer.getBoundingClientRect();
        this.setState({
            translate: {
                x: dimensions.width / 2,
                y: dimensions.height / 2
            }
        });

        this.getTreeData();
    }

    private getTreeData(){
        axios.get(`http://localhost:8081/v1/ledger/tree?account=` + this.state.username)
            .then((res: { data: any; }) => {
                const treeData = res.data;
                this.setState({ treeData });
            })
    }

    render() {
        // @ts-ignore
        return (
            <div style={{ padding: 20 }}>
                <Grid container spacing={5}>
                    <Grid container item xs={3} spacing={1}>
                        <Input color="secondary"
                            // @ts-ignore
                               variant="contained" component="span" type="text" onChange={this.updateInput}/>
                        <Button
                            // @ts-ignore
                               variant="contained" color={"primary"} onClick={this.handleSubmit} placeholder={"Account number"}> Search account </Button>
                    </Grid>
                    <Grid container item xs={6} spacing={1}>
                        <FileUpload/>
                    </Grid>
                </Grid>
                <div style={containerStyles} ref={tc => (this.treeContainer = tc)}>
                    <Tree data={this.state.treeData}
                          translate={this.state.translate}
                          pathFunc={"elbow"}
                          orientation={"vertical"}/>
                </div>
            </div>
        );
    }

}
