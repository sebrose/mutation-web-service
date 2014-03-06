require 'concord'
require 'faraday'
require 'multi_json'
require 'yaml'

# Checkout client namespace
module Checkout
  # Content type used for the REST API
  JSON_CONTENT_TYPE = 'application/json'.freeze
end # Checkout

require 'checkout/client'
require 'checkout/client/error'
require 'checkout/request'
require 'checkout/cli'
require 'checkout/cli/error'
require 'checkout/calculator'
