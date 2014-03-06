module Checkout
  class Client
    # Error raised on unexpected errors
    class Error < RuntimeError
      include Concord.new(:response)

      # Error raised because of unexpected content type
      class ContentType < self

        # Return error message
        #
        # @return [String]
        #
        # @api private
        #
        def message
          "Unexpected response with content type: #{response.headers[:content_type]}"
        end

      end # StatusCode

      # Error raised because of unexpected status code
      class StatusCode < self

        # Return error message
        #
        # @return [String]
        #
        # @api private
        #
        def message
          message = []
          message << "Unexpected response with code: #{response.status}"
          if response.content_type == APPLICATION_JSON
            message << MultiJSON.parse(response.body).inspect
          else
            message << response.body
          end
          message.join
        end

      end # StatusCode

    end # RESTError
  end # Client
end # Checkout
