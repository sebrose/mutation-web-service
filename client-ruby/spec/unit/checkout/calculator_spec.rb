require 'spec_helper'

describe Checkout::Calculator do
  let(:object) { described_class.new(client, batch) }

  let(:client) { double('Client') }
  let(:batch)  { double('Batch') }

  describe '.new' do
    subject { object }

    its(:client) { should be(client) }
    its(:batch)  { should be(batch)  }
  end

  describe '#result' do
    subject { object.result }

    it { should eql({}) }
  end
end
