#!/bin/bash
export PATH="$(pwd)/node/node_modules/npm/node_modules/npm-lifecycle/node-gyp-bin:$(pwd)/node_modules/.bin:$(pwd)/node:$PATH"
echo "PATH corrente $PATH"
npm start
