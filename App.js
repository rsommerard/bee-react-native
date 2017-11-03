import React, { Component } from 'react';
import { Text } from 'react-native';

import Apisense from './Apisense';

Apisense.foo();

export default class App extends Component {
  render() {
    return (
      <Text>Hello world!</Text>
    );
  }
}
