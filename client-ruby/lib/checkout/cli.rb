module Checkout
  # CLI client
  class CLI
    include Concord.new(:client, :arguments)

    ACTIONS = %w(requirements score register advance)

    # Run cli
    #
    # @return [self]
    #
    # @api private
    #
    def run
      unless arguments.length == 1
        usage_error
      end

      argument = arguments.first

      if ACTIONS.include?(argument)
        send(argument)
      else
        usage_error
      end

      self
    end

  private

    # Raise error with specific message
    #
    # @param [String] message
    #
    # @raise [Error]
    #
    # @return [undefined]
    #
    # @api private
    #
    def error(message)
      raise Error, message
    end

    # Advance team
    #
    # @return [undefined]
    #
    # @api private
    #
    def advance
      batch = client.basket_batch
      result = Calculator.new(client, batch).result
      client.submit_batch(result)
    end

    # Print current score
    #
    # @return [undefined]
    #
    # @api private
    #
    def score
      puts client.current_score.fetch('score')
    end

    # Print current requirements
    #
    # @return [undefined]
    #
    # @api private
    #
    def requirements
      puts client.current_requirements.fetch('requirements')
    end

    # Perform registration
    #
    # @return [undefined]
    #
    # @api private
    #
    def register
      client.register
    end

    # Raise usage error
    #
    # @raise [Error]
    #
    # @return [undefined]
    #
    # @api private
    #
    def usage_error
      error("Usage: #{$0} [#{ACTIONS.join('|')}]")
    end

    EXIT_FAILURE = 1
    EXIT_SUCCESS = 0

    # Call command line interface
    #
    # @param [Array<String>] arguments
    #
    # @return [Fixnum]
    #   the exit status
    #
    # @api private
    #
    def self.run(arguments)
      client = Client.from_config_file(Pathname.new(__FILE__).parent.parent.parent.join('config.yml'))
      new(client, arguments).run
      EXIT_SUCCESS
    rescue Error => exception
      $stderr.puts(exception.message)
      EXIT_FAILURE
    end

  end # CLI
end # Checkout
