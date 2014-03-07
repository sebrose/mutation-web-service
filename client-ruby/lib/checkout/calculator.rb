module Checkout
  class Calculator

    # Return client
    #
    # @return [Client]
    #
    # @api private
    #
    attr_reader :client

    # Return batch
    #
    # @return [Hash]
    #
    # @api private
    #
    attr_reader :batch

    # Initialitze object
    #
    # @param [Checkout::Client] client
    #   the rest API client
    #
    # @param [Hash] batch
    #   the curret batch to compute
    #
    # @return [undefined]
    #
    # @api private
    #
    def initialize(client, batch)
      @client, @batch = client, batch
    end

    # Return the computed result
    #
    # @return [Hash]
    #
    # @api private
    #
    def result
      {}
    end

  end # Calculator
end # Checkout
