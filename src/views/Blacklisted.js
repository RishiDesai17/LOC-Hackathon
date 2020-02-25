import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Navbar from './Navbar';
import Box from '@material-ui/core/Box';
import Typography from '@material-ui/core/Typography';

const columns = [

  {
    id: 'rank',
    label: 'Rank',
    minWidth: 10,
    align: 'right',
    format: value => value.toLocaleString(),
  },
  { id: 'Website', label: 'Website', minWidth: 170 },
  { id: 'Category', label: 'Category', minWidth: 100 },
];

function createData(rank,Website,Category) {
  return {rank,Website,Category};
}

const rows = [
  createData(1804,"Tamil Rockers","Torrent"),
  createData(1868,"kyafi.com","media and video streaming"),
  createData(107581,"uploadsat.com","File Storage"),
  createData(432,"torrentz.eu","Torrent Sharing"),
  createData(823,"yourvideohost.com","media and video streaming"),
  createData(10801,"xpau.se","movie piracy"),
  createData(19012,"Sendit.cloud","Cloud sharing"),
  createData(11283,"mega.co.nz","Cloud sharing"),
  createData(9102,"cloud.mail.ru","Cloud sharing"),
  createData(1959,"ingenous.com","R-Rated"),
  createData(22504,"filange.in","Certificate Invalid"),

];

const useStyles = makeStyles({
  root: {
    width: '100%',
  },

});

export default function Blacklisted() {
  const classes = useStyles();
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(10);

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = event => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

  return (
    <div>
    <Navbar />
    <Typography style={{fontSize:"40px",textAlign:"center"}}>
    <Box letterSpacing={6} m={1}>
        Blacklisted Sites Of India
      </Box>
    </Typography>
    <Paper className={classes.root}>
      <TableContainer className={classes.container}>
        <Table stickyHeader aria-label="sticky table">
          <TableHead>
            <TableRow>
              {columns.map(column => (
                <TableCell
                  key={column.id}
                  align={column.align}
                  style={{ minWidth: column.minWidth }}
                >
                  {column.label}
                </TableCell>
              ))}
            </TableRow>
          </TableHead>
          <TableBody>
            {rows.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map(row => {
              return (
                <TableRow hover role="checkbox" tabIndex={-1} key={row.code}>
                  {columns.map(column => {
                    const value = row[column.id];
                    return (
                      <TableCell key={column.id} align={column.align}>
                        {column.format && typeof value === 'number' ? column.format(value) : value}
                      </TableCell>
                    );
                  })}
                </TableRow>
              );
            })}
          </TableBody>
        </Table>
      </TableContainer>
    </Paper>
    </div>
  );
}
