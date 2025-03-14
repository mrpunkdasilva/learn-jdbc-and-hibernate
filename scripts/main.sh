#!/bin/bash

set -e

echo "Diretório atual: $(pwd)"

echo "Unzipping writerside..."
./unzip_writerside.sh

echo "Pushing to remote repository..."
./push_remote_repo.sh

echo "Finished running scripts."